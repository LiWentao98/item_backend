package com.item_backend.mapper;

import com.item_backend.model.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-12 10:36
 */
@Repository
public interface UserMapper {

    // 根据登录信息查询用户
    User searchUserBySchoolAndJobNumber(User user);

    //更新用户信息
    void updateUser(User user);

    //根据用户id和登录密码查询用户
    User searchUserByUIdAndPassword(@Param("u_id") Integer u_id, @Param("password") String password);

    //修改用户登录密码
    void changePassword(@Param("u_id") Integer u_id, @Param("newPassword") String newPassword);

}
