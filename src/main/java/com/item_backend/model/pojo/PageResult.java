package com.item_backend.model.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Description: 分页结果类
 * @Author: Mt.Li
 */

@Data
@ToString
public class PageResult<T> {

    private Long total; // 数据总数
    private List<T> rows; // 数据
    private String message;
    private Integer code; // 返回状态码

    public PageResult(Long total, List<T> rows) {
        // 调用父类无参构造方法,无类型即Object
        super();
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows, String message, Integer code) {
        this.total = total;
        this.rows = rows;
        this.message = message;
        this.code = code;
    }
}
