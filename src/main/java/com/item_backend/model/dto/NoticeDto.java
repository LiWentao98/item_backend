package com.item_backend.model.dto;

import com.item_backend.model.entity.Notice;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Map;

@ApiModel
public class NoticeDto {
    @ApiModelProperty(value = "消息总条数")
    private int total;
    @ApiModelProperty(value = "页面数")
    private int pageNum;
    @ApiModelProperty(value = "当前页的消息数组")
    ArrayList<Notice> notices;

    @ApiModelProperty(value = "消息处理的结果，状态码")
    Map<String ,Integer> meta;


    public Map<String, Integer> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Integer> meta) {
        this.meta = meta;
    }

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

    public ArrayList<Notice> getNotices() {
        return notices;
    }

    public void setNotices(ArrayList<Notice> notices) {
        this.notices = notices;
    }
}
