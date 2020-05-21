package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 题型实体类
 * @Author: Mt.Li
 * @Create: 2020-05-17 16:58
 */
@Data
@ToString
public class QuestionType implements Serializable {

    private static final long serialVersionUID = 6244375693392760389L;

    @ApiModelProperty(value = "题型唯一标识", dataType = "Integer")
    private Integer q_type_id; // 题型唯一标识

    @ApiModelProperty(value = "所属学科id", dataType = "Integer")
    private Integer subject_id; // 所属学科id

    @ApiModelProperty(value = "题型名", dataType = "String")
    private String q_type_name; // 题型名
}
