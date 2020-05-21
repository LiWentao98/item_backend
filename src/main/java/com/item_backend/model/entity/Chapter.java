package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 章节实体类
 * @Author: Mt.Li
 * @Create: 2020-05-17 17:10
 */
@Data
@ToString
public class Chapter implements Serializable {

    private static final long serialVersionUID = 5567409779135466708L;

    @ApiModelProperty(value = "章节唯一标识", dataType = "Integer")
    private Integer chapter_id; // 章节唯一标识

    @ApiModelProperty(value = "所属学科id", dataType = "Integer")
    private Integer subject_id; // 所属学科id

    @ApiModelProperty(value = "章节名", dataType = "String")
    private String chapter_name; // 章节名
}
