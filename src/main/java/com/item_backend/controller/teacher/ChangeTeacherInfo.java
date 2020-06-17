package com.item_backend.controller.teacher;

import com.item_backend.mapper.TeacherInfoMapper;
import com.item_backend.model.entity.User;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
     auth : tong
 */
@RestController
@RequestMapping("/teacher")
public class ChangeTeacherInfo {

    @Autowired
    TeacherInfoMapper teacherInfoMapper;

    @PostMapping("/addTeacher")
    @ApiOperation("获取通知消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "u_school", value = "所属学校名(非空)", defaultValue = "清华大学"),
            @ApiImplicitParam(name = "job_number", value = "职工号(非空)", defaultValue = "123"),
            @ApiImplicitParam(name = "name", value = "姓名", defaultValue = "隔壁老王"),
            @ApiImplicitParam(name = "id_number", value = "身份证号(非空)", defaultValue = "456123789"),
            @ApiImplicitParam(name = "email", value = "邮箱", defaultValue = "1111111"),
            @ApiImplicitParam(name = "telephone", value = "电话(非空)", defaultValue = "111111"),
            @ApiImplicitParam(name = "u_type", value = "用户类型(非空)", defaultValue = "2"),
            @ApiImplicitParam(name = "password", value = "密码(非空)", defaultValue = "123"),
            @ApiImplicitParam(name = "u_state", value = "用户状态(非空)", defaultValue = "1")
    })
    public Result addTeacher(User user){
        teacherInfoMapper.addTeacher(user);
        Result result=new Result(StatusCode.OK,"添加成功",null);
        return result;
    }

    @ApiOperation("获取老师详细信息")
    @GetMapping("/getTeacherById/{id}")
    @ApiImplicitParam(name = "id", value = "教师id", defaultValue = "9")
    public Result getTeacherInfo(@PathVariable("id") Integer id){
        System.out.println("-------->"+id);
      User user= teacherInfoMapper.findUserById(id);
      if (user!=null){
          user.setPassword("");
      }
      Result result=new Result(StatusCode.OK,"获取信息成功",user);
      return result;
    }

    @ApiOperation("获取老师详细信息")
    @DeleteMapping("/deleteTeacher/{id}")
    @ApiImplicitParam(name = "id", value = "教师id",required = true, defaultValue = "9")
    public Result deleteTeacherById(@PathVariable("id") Integer id ){
      int num=  teacherInfoMapper.deleteTeacherById(id);
      String s ="删除成功";

      if (num == 0){
        s= "该用户不存在";
      }
      Result result=new Result(StatusCode.OK,s,null);
      return result;
    }
}
