package com.item_backend.model.dto;

import com.item_backend.model.entity.Chapter;
import com.item_backend.model.entity.Major;
import com.item_backend.model.entity.QuestionType;
import com.item_backend.model.entity.Subject;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-18 22:47
 */
@Data
@ToString
public class SubjectDto implements Serializable {

    private static final long serialVersionUID = -5709495153936744281L;

    private Subject subject;

    private Major major;

    private List<Chapter> characters;

    private List<QuestionType> questionTypes;
}
