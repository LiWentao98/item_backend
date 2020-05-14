package com.item_backend.model.dto;

import lombok.Data;

/**
 * @Author xiao
 * @Time 2020/5/12
 * @Describe 用于返回给前端UEditor的信息实体类
 **/
@Data
public class UEditorFile {
    private String state;
    private String url;
    private String title;
    private String original;
}
