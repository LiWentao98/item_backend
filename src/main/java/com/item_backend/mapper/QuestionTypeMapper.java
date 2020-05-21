package com.item_backend.mapper;

import com.item_backend.model.entity.QuestionType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface QuestionTypeMapper {

    // 根据学科id查询题型
    List<QuestionType> searchQuestionTypeByMajorId(Integer id);

    // 添加题型
    Boolean addQuestionType(QuestionType questionType);

    // 根据题型id删除题型
    Boolean deleteQuestionType(Integer id);
}
