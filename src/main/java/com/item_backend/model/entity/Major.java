package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 专业实体类
 * @Author: Mt.Li
 * @Create: 2020-05-17 18:14
 */
@Data
@ToString
public class Major implements Serializable {

    private static final long serialVersionUID = -705205019503328208L;

    @ApiModelProperty(value = "专业唯一标识", dataType = "Integer")
    private Integer major_id; // 专业唯一标识

    @ApiModelProperty(value = "所属院系id", dataType = "Integer")
    private Integer faculty_id; // 所属院系id

    @ApiModelProperty(value = "专业名", dataType = "String")
    private String major_name; // 专业名
}
