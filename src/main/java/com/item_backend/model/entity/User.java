package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-12 10:23
 */
@Data
@ToString
public class User implements Serializable{

    private static final long serialVersionUID = -3555232002013997911L;

    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer u_id; // 用户唯一标识

    @ApiModelProperty(value = "用户类型", dataType = "Integer")
    private Integer u_type; // 用户类型

    @ApiModelProperty(value = "所属学校名", dataType = "String")
    private String u_school; // 所属学校名

    @ApiModelProperty(value = "所属院系", dataType = "String")
    private String u_faculty; // 所属院系

    @ApiModelProperty(value = "职工号", dataType = "String")
    private String job_number; // 职工号

    @ApiModelProperty(value = "登录密码", dataType = "String")
    private String password; // 登录密码

    @ApiModelProperty(value = "姓名", dataType = "String")
    private String name; // 姓名

    @ApiModelProperty(value = "身份证号", dataType = "String")
    private String id_number; // 身份证号

    @ApiModelProperty(value = "邮箱", dataType = "String")
    private String email; // 邮箱

    @ApiModelProperty(value = "绑定手机", dataType = "String")
    private String telephone; // 绑定手机

    @ApiModelProperty(value = "操作学科", dataType = "Integer")
    private Integer operate_subject; // 操作学科

    @ApiModelProperty(value = "用户状态", dataType = "Integer")
    private Integer u_state; // 用户状态

     /*
     u_type
      {u_type: 1, u_type_name: "超级管理员"},
      {u_type: 2, u_type_name: "校级管理员"},
      {u_type: 3, u_type_name: "院级管理员"},
      {u_type: 4, u_type_name: "命题教师"},
      {u_type: 5, u_type_name: "审核教师"}

     用户状态
     “0：注销”、“1：正常”、“2：限制登录”
    */
}