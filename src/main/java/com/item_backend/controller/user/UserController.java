package com.item_backend.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.entity.User;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.UserServiceImpl;
import com.item_backend.utils.FormatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-12 11:15
 */
@Api(tags = "User_api", description = "User_api", basePath = "/user")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @Autowired
    FormatUtil formatUtil;

    /**
     * 用户登录
     * @param user
     * @return Result：状态码+msg+(data)
     */
    @ApiOperation(value = "用户登录",notes = "Result：状态码+msg+(data)", httpMethod = "POST")
    @PostMapping("/login")
    public Result login(User user) {
        if (!formatUtil.checkStringNull(user.getJob_number(), user.getPassword())) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        try {
            Map map = null;
            try {
                map = userServiceImpl.login(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            if (map.containsKey("msg")){
                return Result.create(StatusCode.LOGINERROR, map.get("msg").toString());
            }
            return Result.create(StatusCode.OK, "登录成功", map);
        } catch (RuntimeException re) {
            return Result.create(StatusCode.LOGINERROR, re.getMessage());
        }
    }

    /**
     * 用户登出
     * @return Result：状态码+msg
     */
    @ApiOperation(value = "用户登出",notes = "Result：状态码+msg;删除redis中的key", httpMethod = "GET")
    @GetMapping("/logout")
    public Result logout() {
        if(userServiceImpl.logout()) {
            return Result.create(StatusCode.OK, "退出成功");
        }
        return Result.create(StatusCode.ERROR, "退出失败");
    }
}
