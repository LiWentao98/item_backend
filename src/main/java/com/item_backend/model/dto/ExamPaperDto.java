package com.item_backend.model.dto;

import com.item_backend.model.entity.ExamPaper;
import com.item_backend.model.entity.Question;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @Author xiao
 * @Time 2020/6/14
 * @Description TODO
 **/
@Data
@ToString
public class ExamPaperDto {

    private ExamPaper examPaper;

    private List<Question> questionList;
}
