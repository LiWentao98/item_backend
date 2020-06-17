package com.item_backend.controller.user;
import com.item_backend.anno.SuperManagerAnno;
import com.item_backend.model.entity.UserType;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.UserTypeService;
import com.item_backend.utils.JwtTokenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/user_type")
public class UserTypeController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserTypeService userTypeService;

    @ApiOperation(value = "添加用户类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userTypeName",required = true, value = "用户类型名(非空)"),
            @ApiImplicitParam(name = "parent_u_type",required = true, value = "默认所属于超级管理员之下（可选填）", defaultValue = "1"),
    })
    @SuperManagerAnno
    @PostMapping("/add_user_type")
    //f父级类型id]
    public Result addUserType( @RequestBody UserType userType){
        //在service 层判断 用户的token 看看是不是登陆了，并且完成添加功能
       boolean b= userTypeService.addUserType(userType);
        System.out.println(b);
       if (b){
           return  Result.create(StatusCode.OK,"添加用户类型成功");
       }
        return Result.create(StatusCode.ERROR,"添加用户类型失败，原因可能是用户未登陆");
    }

    @ApiOperation(value = "删除用户类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "u_type",required = true, value = "用户类型id(非空，且大于1)"),
    })
    @SuperManagerAnno
    @DeleteMapping("/delete_user_type/{u_type}")
    public Result deleteUserType( @PathVariable("u_type") Integer u_type){
        //1-6 号是不能删除，
        if (u_type <= 6){
            return Result.create(StatusCode.ERROR,"基本类型，不允许删除");
        }
       Boolean b= userTypeService.deleteUserType(u_type);
       if (b){
           return Result.create(StatusCode.OK,"删除成功");
       }
        return Result.create(StatusCode.ERROR,"删除失败");
    }

    @ApiOperation(value = "获取所有用户类型",notes = "通过token 拿到用户id 再查，不需要传参数")
    @GetMapping("/get_all_user_type")
    //获取超级管理员和校级管理员都可以看到数据， 要判断 只能看到，存redis
    public Result getAllUserType(HttpServletRequest request){
        Integer uId = jwtTokenUtil.getUIDFromRequest(request);
       List<UserType> list= userTypeService.getAllUserType(uId);
       if (list!=null){
           return Result.create(StatusCode.OK,"获取数据成功",list);
       }
        return Result.create(StatusCode.ERROR,"权限不够");
    }

    @ApiOperation(value = "修改  用户类型")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "u_type",required = true, value = "非空，必填"),
            @ApiImplicitParam(name = "u_type_name", value = "用户类型名(可选择)--创建时已经有默认值"),
            @ApiImplicitParam(name = "u_power", value = "用户类型名权限(可选择)"),
            @ApiImplicitParam(name = "parent_u_type", value = "默认所属于超级管理员之下（可选择）")
    })
    @SuperManagerAnno
    @PutMapping("/update_user_type")
    public Result updateUserType(@RequestBody UserType userType){
        Boolean b=  userTypeService.updateUserType(userType);
        if (b){
            return Result.create(StatusCode.OK,"修改成功");
        }
       return Result.create(StatusCode.ERROR,"更新失败,可能的原因：超级管理员不允许更改。用户类型不存在");
    }
}
















