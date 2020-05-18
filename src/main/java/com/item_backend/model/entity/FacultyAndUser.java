package com.item_backend.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("FacultyAndUser")
public class FacultyAndUser implements Serializable {
    @ApiModelProperty(value = "院系id", dataType = "Integer")
    private Integer faculty_id;
    @ApiModelProperty(value = "院系名称", dataType = "String")
    private String faculty_name;
    @ApiModelProperty(value = "所属学校", dataType = "String")
    private String school;
    @ApiModelProperty(value = "用户id", dataType = "Integer")
    private Integer u_id;
    @ApiModelProperty(value = "用户名", dataType = "String")
    private String name;
    @ApiModelProperty(value = "用户状态", dataType = "Integer")
    private Integer u_state;

    public Integer getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(Integer faculty_id) {
        this.faculty_id = faculty_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getU_state() {
        return u_state;
    }
    public void setU_state(Integer u_state) {
        this.u_state = u_state;
    }

    @Override
    public String toString() {
        return "FacultyAndUser{" +
                "faculty_id=" + faculty_id +
                ", faculty_name='" + faculty_name + '\'' +
                ", school='" + school + '\'' +
                ", u_id=" + u_id +
                ", name='" + name + '\'' +
                ", u_state='" + u_state + '\'' +
                '}';
    }
}
