package com.item_backend.mapper;

import com.item_backend.model.entity.Major;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface MajorMapper {

    // 根据专业id查询专业信息
    Major searchMajorById(Integer id);
}
