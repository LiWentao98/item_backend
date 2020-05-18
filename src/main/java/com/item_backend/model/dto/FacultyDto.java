package com.item_backend.model.dto;

import com.item_backend.model.entity.FacultyAndUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class FacultyDto {
    @ApiModelProperty(value = "消息总条数")
    private int total;

    @ApiModelProperty(value = "页面数")
    private int pageNum;

    @ApiModelProperty(dataType = "List",example = "{faculty_id: 1, faculty_name: \"计算机科学学院\", school: \"长江大学\", u_id: 1, name: \"正经仁\", u_state: \"正常\"}")
    private List<FacultyAndUser> facultyList;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<FacultyAndUser> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<FacultyAndUser> facultyList) {
        this.facultyList = facultyList;
    }
}
