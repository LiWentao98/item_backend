package com.item_backend.service;

import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.PageQueryInfo;
import com.item_backend.model.pojo.Result;

import java.util.Map;

public interface NoticeService {
    Result getNoticeList(PageQueryInfo pageQueryInfo);
    Map saveNoticeService(Notice notice);
    Map deleteNoticeByIdService(int id);
}
