package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-20 10:54
 */
public class Faculty implements Serializable {

    private static final long serialVersionUID = 3250079458755760751L;

    @ApiModelProperty(value = "院系唯一标识", dataType = "Integer")
    private Integer faculty_id; //院系唯一标识

    @ApiModelProperty(value = "院级管理员用户id", dataType = "Integer")
    private Integer u_id; //院级管理员用户id

    @ApiModelProperty(value = "院系名", dataType = "String")
    private String faculty_name; //院系名

    @ApiModelProperty(value = "所属学校", dataType = "String")
    private String school; //所属学校
}
