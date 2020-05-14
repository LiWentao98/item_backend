package com.item_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {

    // 用户登录
    Map login(User user) throws JsonProcessingException;

    // 根据登录信息查询用户
    User searchUserByLoginMsg(User user);

    // 用户登出
    boolean logout();

    // 获取个人信息
    Map getProfile(String token);

    // 修改个人信息
    Map updateUserDetail(String token, User user) throws JsonProcessingException;

    // 修改登录密码
    Map changePassword(String token, String oldPassword, String newPassword);
}
