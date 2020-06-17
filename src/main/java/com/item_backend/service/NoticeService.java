package com.item_backend.service;

import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.PageQueryInfo;
import com.item_backend.model.pojo.PageResult;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

public interface NoticeService {


    Integer  saveManagerNoticeService(Notice notice);
    Integer  saveSuperManagerNoticeService(Notice notice);
    Integer saveNoticeService(Notice notice);

   Integer deleteManagerNoticeByNoticeIdService(Integer id);
   Integer deleteSuperManagerNoticeByNoticeIdService(Integer id);
   Integer deleteNoticeService(Integer id);

    List<Notice> getSuperManagerNoticeService(Integer school_id);
    PageResult<Notice> getManagerNoticeService(PageQueryInfo pageQueryInfo, Integer school_id);
    Map getNoticeService(PageQueryInfo pageQueryInfo,Integer school_id);

    Integer getSchoolIdForNoticeUidService(Notice notice);
    Integer getSchoolIdFromNidService(Integer n_id);

    Integer countAllManagerNoticeBySchoolId(Integer school_id);
}
