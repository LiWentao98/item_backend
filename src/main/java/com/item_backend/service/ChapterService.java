package com.item_backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.entity.Chapter;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Service
public interface ChapterService {

    // 添加章节
    Boolean addChapter(Integer facultyId, Chapter chapter) throws JsonProcessingException;

    // 删除章节
    Boolean deleteChapter(Integer facultyId, Integer chapterId) throws JsonProcessingException;
}
