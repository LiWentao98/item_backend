package com.item_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.dto.SubjectDto;
import com.item_backend.model.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Service
public interface SubjectService {

    // 查询学院学科数目
    Integer getSubjectCount(Integer facultyId);

    // 查询学科列表
    List<SubjectDto> SearchSubjectList(Integer facultyId, Integer page, Integer showCount) throws JsonProcessingException;

    // 添加学科
    Boolean addSubject(Integer facultyId, Subject subject) throws JsonProcessingException;

    // 根据学科id删除学科
    Boolean deleteSubjectById(Integer facultyId, Integer subjectId) throws JsonProcessingException;

    // 更新redis中Subject信息
    void updateSubjectInRedis(Integer facultyId) throws JsonProcessingException;

}
