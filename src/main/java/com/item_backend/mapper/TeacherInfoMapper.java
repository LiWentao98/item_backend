package com.item_backend.mapper;

import com.item_backend.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeacherInfoMapper {
    Integer addTeacher(User user);
    User findUserById(int id);
    int deleteTeacherById(int id);
}
