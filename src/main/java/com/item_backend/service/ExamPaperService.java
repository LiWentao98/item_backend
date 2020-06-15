package com.item_backend.service;

import com.item_backend.model.dto.ExamPaperDto;
import com.item_backend.model.entity.ExamPaper;
import com.item_backend.model.pojo.PageResult;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author xiao
 * @Time 2020/6/14
 * @Description TODO
 **/
@Service
public interface ExamPaperService {
    //获取我的试题
    PageResult<ExamPaper> getMyExamPapers(String token, Integer page, Integer showCount);

    //获取试题详情
    Map getExamPaperDetail(Integer e_id);

    //手动组卷
    Boolean saveExamPaper(String token, ExamPaperDto examPaperDto);

    //删除试卷
    Boolean deleteExamPaper(String token, Integer e_id);
}
