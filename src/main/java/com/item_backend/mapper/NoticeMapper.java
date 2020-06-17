package com.item_backend.mapper;

import com.item_backend.model.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NoticeMapper {

     List<Notice> getSuperManagerNotice(Integer school_id);
     List<Notice> getManagerNotice(Integer school_id);

      Integer saveManagerNotice(Notice notice);
      Integer saveSuperManagerNotice(Notice notice);

     Integer deleteSuperManagerNoticeByNoticeId(Integer id);
     Integer deleteManagerNoticeByNoticeId(Integer id);
     Integer getSchoolIdForNoticeUid(Notice notice);
     Integer getSchoolIdFromNid(Integer n_id);
    Integer countAllManagerNoticeBySchoolId(Integer school_id);
}
