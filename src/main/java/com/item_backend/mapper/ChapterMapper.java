package com.item_backend.mapper;

import com.item_backend.model.entity.Chapter;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface ChapterMapper {

    // 根据学科id查询章节
    List<Chapter> searchChapterBySubjectId(Integer subject_id);

    // 添加章节
    Boolean addChapter(Chapter chapter);

    // 根据章节id删除章节
    Boolean deleteChapter(Integer id);
}
