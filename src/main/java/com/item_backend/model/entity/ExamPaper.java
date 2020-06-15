package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author xiao
 * @Time 2020/6/14
 * @Description 试卷类
 **/
@Data
@ToString
public class ExamPaper implements Serializable {

    private static final long serialVersionUID = 3563405212825808761L;

    @ApiModelProperty(value = "试卷id", dataType = "Integer")
    private Integer e_id;//试卷唯一标识

    @ApiModelProperty(value = "试卷标题", dataType = "String")
    private String e_title;//试卷标题

    @ApiModelProperty(value = "所属学科", dataType = "String")
    private String e_subject;//所属学科

    @ApiModelProperty(value = "组卷方式", dataType = "String")
    private String finish_type;//组卷方式

    @ApiModelProperty(value = "组卷时间", dataType = "Date")
    private Date finish_time;//组卷时间

    @ApiModelProperty(value = "区分度", dataType = "Float")
    private Float distinction;//区分度

    @ApiModelProperty(value = "所属用户id", dataType = "Integer")
    private Integer u_id;//所属用户id
}
