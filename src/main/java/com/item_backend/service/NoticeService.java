package com.item_backend.service;

import com.item_backend.model.dto.NoticeDto;
import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.NoticeQueryInfo;

import java.util.List;
import java.util.Map;

public interface NoticeService {
    NoticeDto getNoticeList(NoticeQueryInfo noticeQueryInfo);
    Map saveNoticeService(Notice notice);
    Map deleteNoticeByIdService(int id);
}
