package com.item_backend.controller.email;

import com.alibaba.druid.util.StringUtils;
import com.item_backend.mapper.UserMapper;
import com.item_backend.model.entity.User;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.utils.EmailUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Autowired
    UserMapper userMapper;

    @Resource
    JavaMailSender javaMailSender;

    @Autowired
    EmailUtil emailUtil;

    @Qualifier("myCacheManager")
    @Autowired
    CacheManager CacheManager;

    @ApiOperation("获取验证码")
    @GetMapping("/get_check_code")
    //提供的学校名和职工id 获取用户所有信息
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "u_school",required = true, value = "用户所属学校（必填）"),
            @ApiImplicitParam(name = "job_number",required = true, value = "职工号（必填）"),
    })
    public Result getCheckCode(User user){
        if (StringUtils.isEmpty(user.getU_school())||StringUtils.isEmpty(user.getJob_number())){
            return Result.create(StatusCode.ERROR,"参数没有添加完整");
        }
           User user1= userMapper.searchUserBySchoolAndJobNumber(user);
           if (user1!=null) {
               String email =  user1.getEmail();
               System.out.println("用户的-俄卖弄----》"+email);
               SimpleMailMessage message = new SimpleMailMessage();
               message.setFrom("337538196@qq.com");
               message.setTo(email);
               message.setSubject("验证码为： ");
               String checkCode = emailUtil.getCheckCode();
               message.setText(checkCode);
               CacheManager.getCache("emailControl").put(user1.getU_id(),checkCode);
               javaMailSender.send(message);
               user1.setPassword("");
               return Result.create(StatusCode.OK,"邮件已经发送，请查收",user1);
           }

        return Result.create(StatusCode.ERROR,"用户不存在");
    }

    @PutMapping("/change_password")
    @ApiOperation("修改密码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "u_id",required = true, value = "用户id（必填）"),
            @ApiImplicitParam(name = "checkCode", required = true,value = "激活码（必填）"),
            @ApiImplicitParam(name = "newPassword",required = true, value = "新密码（必填）"),
    })
    public Result changePassword(@RequestParam("u_id") Integer u_id,@RequestParam("checkCode") String checkCode,@RequestParam("newPassword") String newPassword){
        if (u_id==null|| StringUtils.isEmpty(checkCode)||StringUtils.isEmpty(newPassword)){
            return  Result.create(StatusCode.ERROR,"缺少参数");
        }

       Cache.ValueWrapper redisCheckCode= CacheManager.getCache("emailControl").get(u_id);
        System.out.println(redisCheckCode.get());
       if ( redisCheckCode!=null){
           if (redisCheckCode.get().toString().equals(checkCode)){
               userMapper.changePassword(u_id,newPassword);
               return  Result.create(StatusCode.OK,"修改密码成功,请用新密码登陆");
           }else {
               return  Result.create(StatusCode.ERROR,"请重新获取验证码");
           }
       }

        return  Result.create(StatusCode.ERROR,"请重新获取验证码");
    }
}
