package com.item_backend.mapper;

import com.item_backend.model.entity.Question;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author xiao
 * @Time 2020/6/11
 * @Description TODO
 **/
@Repository
public interface QuestionMapper {
    //添加试题
    int addQuestion(Question question);

    //更新试题信息
    int updateQuestion(Question question);

    //根据试题id查询试题
    Question searchQuestionByQId(Integer q_id);

    //根据试题属性查询试题
    List<Question> searchQuestionsByProperties(@Param("question") Question question, @Param("start") Integer start, @Param("showCount") Integer showCount);

    //根据试题属性查询试题数量
    int getQuestionCountByProperties(Question question);

    //根据用户id查询试题
    List<Question> searchQuestionsByUId(@Param("u_id") Integer u_id, @Param("start") Integer start, @Param("showCount") Integer showCount);

    //根据用户id查询试题数量
    int getQuestionCountByUId(Integer u_id);

    //根据操作学科id查询待审试题
    List<Question> searchPendingQuestionsByOperateSubject(@Param("operate_subject") Integer operate_subject, @Param("start") Integer start, @Param("showCount") Integer showCount);

    //根据操作学科id查询待审试题数量
    int getPendingQuestionsCountByOperateSubject(Integer operate_subject);

    //修改试题状态
    int changeQuestionState(@Param("q_id") Integer q_id, @Param("q_state") Integer q_state);

    //根据试卷id获取试题列表
    List<Question> searchQuestionsByEId(Integer e_id);
}
