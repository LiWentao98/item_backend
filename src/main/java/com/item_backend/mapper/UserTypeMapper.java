package com.item_backend.mapper;

import com.item_backend.model.entity.UserType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTypeMapper {

    // 根据类型id查询UserType
    UserType searchUserTypeByUType(Integer u_type);
   Integer addUserType(UserType userType);
   Integer deleteUserTypeByUType(@Param("u_type")  Integer u_type);
   List<UserType> getAllUserType(Integer uId);
   Integer updateUserType(UserType userType);
}
