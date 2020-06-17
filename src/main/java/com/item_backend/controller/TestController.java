package com.item_backend.controller;

import com.item_backend.model.entity.TestUser;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.TestUserServiceImpl;
import com.item_backend.utils.FormatUtil;
import com.item_backend.utils.JwtTokenUtil;
import com.item_backend.utils.LoggerUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 测试接口
 * @Author: Mt.Li
 * @Create: 2020-05-10 15:32
 */
@Api(tags = "测试api", description = "测试api", basePath = "/test")
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestUserServiceImpl testUserService;

    @Autowired
    FormatUtil formatUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private Logger logger = LoggerUtil.loggerFactory(this.getClass());

    @ApiOperation(value = "登录案例测试",notes = "用户登录测试Result",httpMethod = "POST")
    @PostMapping("/login")
    public Result loginTest(TestUser user){
        logger.info("登录测试开始");
        try{
            if (!formatUtil.checkStringNull(user.getName(),user.getPassword())){
                // 判断是否为空也可以前端处理
                logger.info("对象为空");
                return Result.create(StatusCode.ERROR,"参数错误");
            }else if (testUserService.searchUser(user)){
                Map<String, Object> map = new HashMap<>(2);
                map.put("username",user.getName());
                map.put("password",user.getPassword());
                logger.info(user.getName() + "登录成功！");
                return Result.create(StatusCode.OK,"登陆成功",map);
            }
        }catch (NullPointerException e){
            logger.info("参数异常");
            return Result.create(StatusCode.ERROR,"参数异常");
        }
        logger.info("用户名或密码错误");
        return Result.create(StatusCode.LOGINERROR,"用户名或密码错误");
    }

    @ApiOperation(value = "测试从token中获取用户id",notes = "token不带前缀",httpMethod = "GET")
    @GetMapping("/test_get_u_id/{token}")
    public Result getTokenUID(@ApiParam("token")@PathVariable("token") String token){
        Integer uId = jwtTokenUtil.getUIDFromToken(token);
        return Result.create(StatusCode.OK,"获取ID成功",uId);
    }

    @ApiOperation(value = "测试从token中获取用户类型名",notes = "token不带前缀bearer",httpMethod = "GET")
    @GetMapping("/test_get_u_type_name/{token}")
    public Result getTokenUTypeName(@ApiParam("token")@PathVariable("token") String token){
        String uTypename = jwtTokenUtil.getRolesFromToken(token);
        return Result.create(StatusCode.OK,"获取用户类型名成功",uTypename);
    }
}
