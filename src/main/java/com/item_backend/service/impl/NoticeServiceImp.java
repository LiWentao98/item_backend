package com.item_backend.service.impl;
import com.github.pagehelper.PageHelper;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.NoticeMapper;
import com.item_backend.model.entity.Notice;
import com.item_backend.model.entity.PageQueryInfo;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.*;


@Service
public class NoticeServiceImp implements NoticeService {
    @Resource
    NoticeMapper noticeMapper;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Qualifier("myCacheManager")
    @Autowired
    CacheManager myCacheManager;

    @Override
    public Integer saveManagerNoticeService(Notice notice) {
        return  noticeMapper.saveManagerNotice(notice);
    }

    @Override
    public Integer saveSuperManagerNoticeService(Notice notice) {
         return  noticeMapper.saveSuperManagerNotice(notice);
    }

    @Override
    public Integer saveNoticeService(Notice notice) {
        Integer school_id= getSchoolIdForNoticeUidService(notice);
        Integer changerrow=0;
        /*超级管理原school_id=0*/
        if (school_id != null){
            if (school_id>0){
                changerrow=  saveManagerNoticeService(notice);
            }else {
                changerrow=  saveSuperManagerNoticeService(notice);
            }
            /*保存数据时需要清空 notice 对应的缓存区*/
            if (changerrow>0){
                myCacheManager.getCache(RedisConfig.REDIS_NOTICE_SERVICE).clear();
            }
            return changerrow;
        }
        return changerrow;
    }

    @Override
    public Integer deleteManagerNoticeByNoticeIdService(Integer n_id) {
        return noticeMapper.deleteManagerNoticeByNoticeId(n_id);
    }

    @Override
    public Integer deleteSuperManagerNoticeByNoticeIdService(Integer n_id) {
        return noticeMapper.deleteSuperManagerNoticeByNoticeId(n_id);
    }

    @Override
    public Integer deleteNoticeService(Integer n_id) {
        Integer school_id = getSchoolIdFromNidService(n_id);
        Integer changerRow=0;

        if (school_id!=null){
            if (school_id>0){
                changerRow=  deleteManagerNoticeByNoticeIdService(n_id);
            }else {
                changerRow=  deleteSuperManagerNoticeByNoticeIdService(n_id);
            }

            /*删除数据时需要清空 notice 对应的缓存区*/
            if (changerRow>0){
                myCacheManager.getCache(RedisConfig.REDIS_NOTICE_SERVICE).clear();
            }
            return changerRow;
        }
        return changerRow;
    }


    @Override
    public List<Notice> getSuperManagerNoticeService(Integer school_id) {
        System.out.println("-------->SuperManagerNoticeService");
        if (school_id!=null){
            if (school_id==0){
                PageHelper.startPage(1,3);
                return noticeMapper.getSuperManagerNotice(school_id);
            }
        }
       return null;
    }


    @Override
    public PageResult<Notice> getManagerNoticeService(PageQueryInfo pageQueryInfo, Integer school_id) {
        System.out.println("-------->ManagerNoticeService");
        if (school_id!=null) {
            if (school_id > 0) {
                /*mybatis查到学校 但是学校没有发消息，返回list的化 是一个空的list【】,如果是查对象找不到就是空null*/
                PageHelper.startPage(pageQueryInfo.getPageNum(), pageQueryInfo.getPageSize());
                List<Notice> managerNoticeList=noticeMapper.getManagerNotice(school_id);
                PageResult<Notice> pageResult=new PageResult<Notice>(noticeMapper.countAllManagerNoticeBySchoolId(school_id),managerNoticeList);
                return pageResult;
            }
        }
        return null;

    }

    @Override
    @Cacheable(cacheNames = RedisConfig.REDIS_NOTICE_SERVICE,key ="#school_id+'_'+#pageQueryInfo.pageNum+'_'+#pageQueryInfo.pageSize" )
    public Map getNoticeService(PageQueryInfo pageQueryInfo, Integer school_id) {
        //如果查询不到数据，返回的消息都是为null
        PageResult<Notice> managerPageResult = getManagerNoticeService(pageQueryInfo,school_id);
        List<Notice> superManagerNoticeList = getSuperManagerNoticeService(0);
        Map map=new HashMap();
        map.put("managerNoticeList",managerPageResult);
        map.put("superManagerNoticeList",superManagerNoticeList);
        return map;
    }

    @Override
    public Integer getSchoolIdForNoticeUidService(Notice notice) {
        return noticeMapper.getSchoolIdForNoticeUid(notice);
    }

    @Override
    public Integer getSchoolIdFromNidService(Integer n_id) {
        return noticeMapper.getSchoolIdFromNid(n_id);
    }

    @Override
    public Integer countAllManagerNoticeBySchoolId(Integer school_id) {
      Integer total=  noticeMapper.countAllManagerNoticeBySchoolId(school_id);
        return total;
    }

}
