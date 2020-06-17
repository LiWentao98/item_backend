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


    private Integer total; // 数据总数
    private List<T> rows; // 数据

    public PageResult(Integer total, List<T> rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

}
