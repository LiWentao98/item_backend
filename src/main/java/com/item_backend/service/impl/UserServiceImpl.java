package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.item_backend.config.JwtConfig;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.UserMapper;
import com.item_backend.mapper.UserTypeMapper;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.User;
import com.item_backend.model.entity.UserType;
import com.item_backend.service.UserService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Mt.Li & xiao
 * @Create: 2020-05-12 11:16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserTypeMapper userTypeMapper;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 用户登录
     *
     * @param user
     * @return map
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map login(User user) throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        // 判断是否存在用户
        User user1 = searchUserByLoginMsg(user);
        if (user1 == null || user1.getJob_number() == null || !user1.getPassword().equals(user.getPassword())) {
            map.put("msg", "用户名或密码错误");
            return map;
        }
        // 判断用户是否被封禁
        if (user1.getU_state() == 0) {
            map.put("msg", "用户被已被注销");
            return map;
        } else if (user1.getU_state() == 2) {
            map.put("msg", "用户被限制登录");
            return map;
        }
        // 判断用户是否已登录
        if (redisTemplate.hasKey(JwtConfig.REDIS_TOKEN_KEY_PREFIX + user1.getU_id())) {
            map.put("msg", "该用户已登录");
            return map;
        }
        // 判断用户类型是否正确
        if (!user.getU_type().equals(user1.getU_type())) {
            map.put("msg", "请选择正确的用户类型");
            return map;
        }
        UserDto userDto = new UserDto();
        UserType userType = userTypeMapper.searchUserTypeByUType(user1.getU_type());
        userDto.setUser(user1);
        userDto.setUserType(userType);

        // 根据用户详细信息生成token
        final String token = jwtTokenUtil.generateToken(userDto);

        map.put("token", jwtConfig.getPrefix() + token);
        map.put("userMsg", userDto);

        // 向redis中存储个人信息
        redisTemplate.opsForValue().
                set(RedisConfig.REDIS_USER_MESSAGE + user1.getU_id(), objectMapper.writeValueAsString(userDto), jwtConfig.getTime(), TimeUnit.SECONDS);

        // 向redis中存储token（设置过期时间30min）
        redisTemplate.opsForValue().
                set(JwtConfig.REDIS_TOKEN_KEY_PREFIX + user1.getU_id(), jwtConfig.getPrefix() + token, jwtConfig.getTime(), TimeUnit.SECONDS);
        return map;
    }

    /**
     * 根据登录信息查询用户
     *
     * @param user
     * @return User
     */
    @Override
    public User searchUserByLoginMsg(User user) {
        return userMapper.searchUserBySchoolAndJobNumber(user);
    }

    /**
     * 退出登录
     * 删除redis中的key
     */
    public boolean logout() {
        try{
            Integer uId = jwtTokenUtil.getUIDFromRequest(request);
            redisTemplate.delete(JwtConfig.REDIS_TOKEN_KEY_PREFIX + uId);
            return true;
        }catch (NullPointerException e){
            return false;
        }
    }

    /**
     * 获取个人信息
     *
     * @param
     * @return Map
     * @Author xiao
    */
    @Override
    public Map getProfile(String token) {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        Map<String, Object> map = new HashMap<>();
        // 判断u_id的值
        if (u_id == null) {
            map.put("msg", "无效的token");
            return map;
        }
        String userDtoJSON = redisTemplate.opsForValue().get(RedisConfig.REDIS_USER_MESSAGE + u_id);
        UserDto userDto = JSON.parseObject(userDtoJSON, UserDto.class);

        map.put("userMsg", userDto);
        return map;
    }

    /**
     * 修改个人信息
     *
     * @param
     * @return Map
     * @Author xiao
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map updateUserDetail(String token, User user) throws JsonProcessingException {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        Map<String, Object> map = new HashMap<>();
        // 判断u_id的值
        if (u_id != user.getU_id()) {
            map.put("msg", "当前修改的不是本人信息，请重新登录后再试一次");
            return map;
        }
        userMapper.updateUser(user);

        // 更新redis中存储的个人信息
        String userDtoJSON = redisTemplate.opsForValue().get(RedisConfig.REDIS_USER_MESSAGE + u_id);
        UserDto userDto = JSON.parseObject(userDtoJSON, UserDto.class);
        userDto.setUser(user);
        redisTemplate.opsForValue().
                set(RedisConfig.REDIS_USER_MESSAGE + u_id, objectMapper.writeValueAsString(userDto), jwtConfig.getTime(), TimeUnit.SECONDS);

        return map;
    }

    /**
     * 修改登录密码
     *
     * @param
     * @return Map
     * @Author xiao
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map changePassword(String token, String oldPassword, String newPassword) {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        Map<String, Object> map = new HashMap<>();
        // 判断u_id的值
        if (u_id == null) {
            map.put("msg", "无效的token");
            return map;
        }
        //判断旧密码是否正确
        User user = userMapper.searchUserByUIdAndPassword(u_id, oldPassword);
        if (user == null) {
            map.put("msg", "旧密码错误");
            return map;
        }
        userMapper.changePassword(u_id, newPassword);
        return map;
    }
}
