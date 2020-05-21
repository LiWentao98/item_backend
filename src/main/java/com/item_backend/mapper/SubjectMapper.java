package com.item_backend.mapper;

import com.item_backend.model.entity.Subject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
*/
@Repository
public interface SubjectMapper {

    // 查询学院学科数量
    Integer getSubjectCount(Integer faculty_id);

    // 查询学科列表
    List<Subject> searchSubjectList(@Param("page") Integer page, @Param("showCount") Integer showCount);

    // 添加学科
    Boolean addSubject(Subject subject);

    // 根据学科id删除学科
    Boolean deleteSubjectById(Integer id);


}
