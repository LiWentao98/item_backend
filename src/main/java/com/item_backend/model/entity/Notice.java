package com.item_backend.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import java.io.Serializable;
import java.util.Date;

@ApiModel("notice")
public class Notice implements Serializable {
    @ApiModelProperty(value="消息id" ,dataType = "Integer")
    private Integer n_id;
    @ApiModelProperty(value = "消息标题", dataType = "String")
    private String n_title;
    @ApiModelProperty(value = "消息内容",dataType = "String")
    private String n_content;
    @ApiModelProperty(value = "发布时间",dataType = "Data")
    private String publish_time;
    @ApiModelProperty(value = "发布消息的用户的名字",dataType = "String")
    private String u_name;
    @ApiModelProperty(value = "用户id",dataType = "Integer")
    private Integer u_id;

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "n_id=" + n_id +
                ", n_title='" + n_title + '\'' +
                ", n_content='" + n_content + '\'' +
                ", publish_time='" + publish_time + '\'' +
                ", u_name='" + u_name + '\'' +
                ", u_id=" + u_id +
                '}';
    }

    public Integer getN_id() {
        return n_id;
    }

    public void setN_id(Integer n_id) {
        this.n_id = n_id;
    }

    public String getN_title() {
        return n_title;
    }

    public void setN_title(String n_title) {
        this.n_title = n_title;
    }

    public String getN_content() {
        return n_content;
    }

    public void setN_content(String n_content) {
        this.n_content = n_content;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }
}
