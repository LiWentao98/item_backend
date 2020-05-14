package com.item_backend.mapper;

import com.item_backend.model.entity.Notice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper {

     List<Notice> findAllNotice();
     int saveNotice(Notice notice);
     int deleteNoticeById(int id);
}
