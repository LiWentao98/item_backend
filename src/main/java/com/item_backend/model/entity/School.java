package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-20 10:50
 */
@Data
@ToString
public class School implements Serializable {

    private static final long serialVersionUID = -1633420723241924216L;

    @ApiModelProperty(value = "学校唯一标识", dataType = "Integer")
    private Integer school_id; // 学校唯一标识

    @ApiModelProperty(value = "校级管理员用户id", dataType = "Integer")
    private Integer u_id; // 校级管理员用户id

    @ApiModelProperty(value = "学校名", dataType = "String")
    private String school_name; // 学校名
}
