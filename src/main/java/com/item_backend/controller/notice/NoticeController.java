package com.item_backend.controller.notice;

import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.PageQueryInfo;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.NoticeServiceImp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.util.*;

/*
* 在数据库中好区别，设定超级管理员的学校id 为0
* */

@Api("NoticeController")
@RestController
@RequestMapping("/notice")
public class NoticeController {
    @Resource
    NoticeServiceImp noticeServiceImp;

    @Qualifier("myCacheManager")
    @Autowired
    CacheManager myCacheManager;

    @ApiOperation(value = "获取通知消息")
    @GetMapping("/get_notice_list/{school_id}")//+ shcollid 地址栏上不用驼峰
    @ApiImplicitParams({
            @ApiImplicitParam(name = "query", value = "查询关键字", defaultValue = ""),
            @ApiImplicitParam(name = "pageNum",required = true, value = "页号(非空)", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", required = true,value = "每页大小(非空)", defaultValue = "8")
    })

    /*自动封装，如果时字符串没有找到，就是空串， 如果是其他数据类型，没有就是null*/
    @Transactional
    public Result getNoticeList(PageQueryInfo pageQueryInfo, @PathVariable("school_id") Integer SchoolId) {
        System.out.println("schoolid----->"+SchoolId);
      Map map =  noticeServiceImp.getNoticeService(pageQueryInfo,SchoolId);
      PageResult<Notice> managerNoticePageResult= (PageResult<Notice>) map.get("managerNoticeList");
        String msg="获取消息成功";
        if (managerNoticePageResult==null){
            msg="获取用户消息失败,可能的原因是学校不存在";
            return new Result(StatusCode.ERROR,msg);
        }
        /*查到学校，但是该学校一条消息都没有的话，就返回一个空的[] managerNoticeList*/
        return new Result(StatusCode.OK,msg,map);
    }

    @ApiOperation(value = "添加消息")
    @PostMapping("/save_notice")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "n_title",required = true, value = "消息标题", defaultValue = "title"),
            @ApiImplicitParam(name = "n_content",required = true, value = "消息内容", defaultValue = "good good stydy"),
            @ApiImplicitParam(name = "u_name",required = true, value = "提交消息的用户", defaultValue = "hahaha"),
            @ApiImplicitParam(name = "u_id",required = true, value = "用户id", defaultValue = "4")
    })
    @Transactional
    public Result saveNotice(@RequestBody Notice notice) {
     Integer changeRow= noticeServiceImp.saveNoticeService(notice);
     String msg="消息保存成功";
     if (changeRow>0)
        return new Result(StatusCode.OK,msg);
     else {
         msg="消息保存失败，可能的原因是该用户不属于任何学校";
         return new Result(StatusCode.ERROR,msg);
     }
    }


    @ApiOperation(value = "通过公告id删除公告")
    @DeleteMapping("/delete_notice/{id}")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "n_id",required = true, value = "消息id", defaultValue = "42"),
    })
    @Transactional
    public Result deleteNoticeById(@PathVariable("id") int n_id) {
       Integer changeRow =  noticeServiceImp.deleteNoticeService(n_id);
       String msg  = "消息删除成功";
        if (changeRow>0)
            return new Result(StatusCode.OK,msg);
        else {
            msg="消息删除失败，可能的原因是该用户不属于任何学校";
            return new Result(StatusCode.ERROR,msg);
        }
    }
}
