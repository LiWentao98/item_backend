package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-12 15:44
 */
@Data
@ToString
public class UserType implements Serializable {

    private static final long serialVersionUID = 2864122550229118561L;

    @ApiModelProperty(value = "用户类型唯一标识", dataType = "Integer")
    private Integer u_type; // 用户类型唯一标识

    @ApiModelProperty(value = "用户类型名", dataType = "String")
    private String u_type_name; // 用户类型名

    @ApiModelProperty(value = "用户权限", dataType = "String")
    private String u_power; // 用户权限

    @ApiModelProperty(value = "上级用户类型", dataType = "Integer")
    private Integer parent_u_type; //上级用户类型
}
