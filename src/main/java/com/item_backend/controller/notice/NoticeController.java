package com.item_backend.controller.notice;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.item_backend.mapper.NoticeMapper;
import com.item_backend.model.dto.NoticeDto;
import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.NoticeQueryInfo;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.service.impl.NoticeServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.*;


@Api("NoticeController")
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Resource
    NoticeServiceImp noticeServiceImp;

    @ApiOperation("获取通知消息")
    @PostMapping("/getNoticeList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询关键字", defaultValue = ""),
            @ApiImplicitParam(name = "pageNum", value = "页号", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", defaultValue = "8")
    })
    /*自动封装，如果时字符串没有找到，就是空串， 如果是其他数据类型，没有就是null*/
   public NoticeDto getNoticeList(NoticeQueryInfo noticeQueryInfo){
     NoticeDto noticeDto=  noticeServiceImp.getNoticeList(noticeQueryInfo);
       return noticeDto;

    }


    @ApiOperation("保存消息")
    @PostMapping("/saveNotice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "n_id", value = "消息id", defaultValue = "42"),
            @ApiImplicitParam(name = "n_title", value = "消息标题", defaultValue = "title"),
            @ApiImplicitParam(name = "n_content", value = "消息内容", defaultValue = "good good stydy"),
            @ApiImplicitParam(name = "publish_time", value = "提交消息的时间", defaultValue = "2019-7-20 20:20"),
            @ApiImplicitParam(name = "u_name", value = "提交消息的用户", defaultValue = "hahaha"),
            @ApiImplicitParam(name = "u_id", value = "用户id", defaultValue = "4")
    })
    public Map saveNotice(Notice notice){
      Map data= noticeServiceImp.saveNoticeService(notice);
     return data;
    }


    @ApiOperation("通过公告id删除公告")
    @PostMapping("/deleteNotice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "n_id", value = "消息id", defaultValue = "42"),
    })

    public Map deleteNoticeById(int n_id){
     Map data=noticeServiceImp.deleteNoticeByIdService(n_id);
       return data;
    }
}
