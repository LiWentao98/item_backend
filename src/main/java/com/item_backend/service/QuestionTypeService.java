package com.item_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.entity.QuestionType;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Service
public interface QuestionTypeService {

    // 添加题型
    Boolean addQuestionType(Integer facultyId, QuestionType questionType) throws JsonProcessingException;

    // 删除题型
    Boolean deleteQuestionType(Integer facultyId, Integer questionTypeId) throws JsonProcessingException;
}
