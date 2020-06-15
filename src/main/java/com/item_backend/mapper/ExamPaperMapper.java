package com.item_backend.mapper;

import com.item_backend.model.entity.ExamPaper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author xiao
 * @Time 2020/6/14
 * @Description TODO
 **/
@Repository
public interface ExamPaperMapper {
    //添加试卷
    int addExamPaper(ExamPaper examPaper);

    //根据试题id查询试卷
    ExamPaper searchExamPaperByEId(Integer e_id);

    //根据用户id查询试卷
    List<ExamPaper> searchExamPapersByUId(@Param("u_id") Integer u_id, @Param("start") Integer start, @Param("showCount") Integer showCount);

    //根据用户id查询试卷数量
    int getExamPaperCountByUId(Integer u_id);

    //删除试卷
    int deleteExamPaperByEIdAndUId(@Param("e_id") Integer e_id, @Param("u_id") Integer u_id);
}
