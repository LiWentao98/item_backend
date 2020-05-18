package com.item_backend.service.impl;

import com.item_backend.mapper.FacultyAndUserMapper;
import com.item_backend.model.entity.FacultyAndUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FacultyAndUserServiceImp {
    @Resource
    FacultyAndUserMapper facultyAndUserMapper;

  public   List<FacultyAndUser> getFacultyInfo(){
        List<FacultyAndUser> facultyAndUserList= facultyAndUserMapper.findAllFacultyInfo();
        return facultyAndUserList;
    }
}
