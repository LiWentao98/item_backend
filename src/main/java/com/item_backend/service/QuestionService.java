package com.item_backend.service;

import com.item_backend.model.entity.Question;
import com.item_backend.model.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author xiao
 * @Time 2020/6/11
 * @Description TODO
 **/

@Service
public interface QuestionService {

    //条件检索试题
    PageResult<Question> searchQuestion(Question question, Integer page, Integer showCount);

    //获取我的试题
    PageResult<Question> getMyQuestions(String token, Integer page, Integer showCount);

    //获取待审试题列表
    PageResult<Question> getPendingQuestions(String token, Integer page, Integer showCount);

    //获取试题详情
    Map getQuestionDetail(Integer q_id);

    //保存试题
    Boolean saveQuestion(String token, Question question);

    //审核试题
    Boolean examineQuestion(String token, Question question);
}
