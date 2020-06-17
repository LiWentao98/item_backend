package com.item_backend.controller.faculty;

import com.item_backend.mapper.FacultyAndUserMapper;
import com.item_backend.model.entity.FacultyAndUser;
import com.item_backend.model.pojo.PageResult;

import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.FacultyAndUserServiceImp;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/faculty")
public class facultyController {

    @Autowired
    FacultyAndUserServiceImp facultyAndUserServiceImp;

    @GetMapping("/getFacultyInfo")
    @ApiOperation("获取院系信息")
    public PageResult<FacultyAndUser> getFacultyInfo(){

        List<FacultyAndUser> list=   facultyAndUserServiceImp.getFacultyInfo();
        PageResult<FacultyAndUser> result=new PageResult<FacultyAndUser>(2,list);
        return result;
    }
}
