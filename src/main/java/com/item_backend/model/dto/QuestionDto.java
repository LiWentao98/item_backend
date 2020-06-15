package com.item_backend.model.dto;

import com.item_backend.model.entity.Question;
import com.item_backend.model.entity.User;
import lombok.Data;
import lombok.ToString;

/**
 * @Author xiao
 * @Time 2020/6/13
 * @Description TODO
 **/
@Data
@ToString
public class QuestionDto {

    private Question question;

    private User user;
}
