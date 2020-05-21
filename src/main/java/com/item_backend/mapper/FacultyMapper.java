package com.item_backend.mapper;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface FacultyMapper {

    // 根据用户信息的学院名查询学院id
    Integer searchFacultyIdByFacultyName(@Param("facultyName") String facultyName, @Param("schoolName") String schoolName);

    // 根据院级管理员id查询学院id
    Integer searchFacultyIdByAdminId(Integer id);
}
