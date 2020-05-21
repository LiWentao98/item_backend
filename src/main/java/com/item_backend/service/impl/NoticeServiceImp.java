package com.item_backend.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.NoticeMapper;
import com.item_backend.model.dto.NoticeDto;
import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.PageQueryInfo;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoticeServiceImp implements NoticeService {

    @Resource
    NoticeMapper noticeMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Result getNoticeList(PageQueryInfo pageQueryInfo) {
        // 先查询缓存中是否存在
        if(redisTemplate.hasKey(RedisConfig.REDIS_NOTICE + pageQueryInfo.getQuery()+pageQueryInfo.getPageNum()+pageQueryInfo.getPageSize())){

            // 缓存中不存在，先查询所有的学科信息放入redis中
            String result= redisTemplate.opsForValue().get(RedisConfig.REDIS_NOTICE + pageQueryInfo.getQuery()+pageQueryInfo.getPageNum()+pageQueryInfo.getPageSize());
            try {
             Result res=  objectMapper.readValue(result,Result.class);
             return res;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }


        if (pageQueryInfo.getQuery()!=""){ }

        int pageNum=1;
        if (pageQueryInfo.getPageNum()!=null)
            pageNum = pageQueryInfo.getPageNum();
        int pageSize=5;

        if ( pageQueryInfo.getPageSize()!=null)
            pageSize = pageQueryInfo.getPageSize();

        Page page= PageHelper.startPage(pageNum, pageSize);



        ArrayList<Notice> notices= (ArrayList<Notice>) noticeMapper.findAllNotice();

        /*消息按时间排序，最近的消息放在最前边*/
        Collections.sort(notices, new Comparator<Notice>() {
            @Override
            public int compare(Notice o1, Notice o2) {
                //返回正值是代表左侧日期大于参数日期
                int flag = o1.getPublish_time().compareTo(o2.getPublish_time());
                if (flag > 0)
                    return -1;
                return 1;
            }
        });

        NoticeDto noticeDto=new NoticeDto();
        noticeDto.setNotices(notices);
        noticeDto.setPageNum(page.getPageNum());
        noticeDto.setTotal((int) page.getTotal());

        String msg="获取数据成功";
        if (notices.size()==0){
            msg="后台没有数据";
        }

        Result result=new Result(StatusCode.OK,msg,noticeDto);

        try {
            redisTemplate.opsForValue().set(RedisConfig.REDIS_NOTICE + pageQueryInfo.getQuery()+pageQueryInfo.getPageNum()+pageQueryInfo.getPageSize(),
                    objectMapper.writeValueAsString(result));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public Map saveNoticeService(Notice notice) {

        if (notice.getPublish_time()==""){
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
            notice.setPublish_time(dateFormat.format(new Date()));
        }

        int msg=  noticeMapper.saveNotice(notice);

        if (msg>0){
            msg=200;
        }

        Map data =new HashMap();

        Map meta=new HashMap();
        meta.put("status",msg);
        meta.put("msg","增加用户成功");
        data.put("meta",meta);

        return data;
    }

    @Override
    public Map deleteNoticeByIdService(int n_id) {
        int num=  noticeMapper.deleteNoticeById(n_id);

        Map map=new HashMap();
        Map meta=new HashMap();
        if (num>0)
            meta.put("msg","删除了"+num+"条消息");
        else
            meta.put("msg","没有删除消息");

        meta.put("status",200);
        map.put("meta",meta);

        return map;
    }
}
