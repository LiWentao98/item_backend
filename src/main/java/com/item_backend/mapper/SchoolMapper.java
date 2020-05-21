package com.item_backend.mapper;

import org.springframework.stereotype.Repository;
/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface SchoolMapper {

    // 根据用户信息中的学校名查询学校id
    Integer searchSchoolIdBySchoolName(String schoolName);

    // 根据校级管理员的id查询学校id
    Integer searchSchoolIdByAdminId(Integer id);
}
