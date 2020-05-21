package com.item_backend.model.entity;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-17 09:24
 */

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description: 学科实体类
 * @Author: Mt.Li
*/
@Data
@ToString
public class Subject implements Serializable {

    private static final long serialVersionUID = -8683895775560386125L;

    @ApiModelProperty(value = "学科唯一标识", dataType = "Integer")
    private Integer subject_id; // 学科唯一标识

    @ApiModelProperty(value = "学科名", dataType = "String")
    private String subject_name; // 学科名

    @ApiModelProperty(value = "所属门类类别", dataType = "String")
    private String category; // 所属门类类别

    @ApiModelProperty(value = "所属专业id", dataType = "Integer")
    private Integer major_id; // 所属专业id

}
