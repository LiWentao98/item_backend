package com.item_backend.model.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author xiao
 * @Time 2020/6/11
 * @Description 试题类
 **/
@Data
@ToString
public class Question implements Serializable {

    private static final long serialVersionUID = -1132954703477680012L;

    @ApiModelProperty(value = "试题id", dataType = "Integer")
    private Integer q_id;//试题唯一标识

    @ApiModelProperty(value = "门类类别", dataType = "String")
    private String q_category;//门类类别

    @ApiModelProperty(value = "所属专业", dataType = "String")
    private String q_major;

    @ApiModelProperty(value = "所属学科", dataType = "String")
    private String q_subject;

    @ApiModelProperty(value = "所属章节", dataType = "String")
    private String q_chapter;

    @ApiModelProperty(value = "试题状态", dataType = "Integer")
    private Integer q_state;

    @ApiModelProperty(value = "试题题型", dataType = "String")
    private String q_type;

    @ApiModelProperty(value = "试题内容", dataType = "String")
    private String q_content;

    @ApiModelProperty(value = "试题相关图片路径", dataType = "String")
    private String q_image_url;

    @ApiModelProperty(value = "试题难度", dataType = "float")
    private float difficulty;

    @ApiModelProperty(value = "考查的知识点", dataType = "String")
    private String knowledge;

    @ApiModelProperty(value = "搜索关键字、标签", dataType = "String")
    private String tags;

    @ApiModelProperty(value = "试题答案", dataType = "String")
    private String answer;

    @ApiModelProperty(value = "上传时间", dataType = "Date")
    private Date upload_time;

    @ApiModelProperty(value = "审核意见", dataType = "String")
    private String opinion;

    @ApiModelProperty(value = "上传者id", dataType = "Integer")
    private Integer u_id;

}
