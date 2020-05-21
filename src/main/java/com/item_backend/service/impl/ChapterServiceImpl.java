package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.ChapterMapper;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.Chapter;
import com.item_backend.service.ChapterService;
import com.item_backend.service.SubjectService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-18 22:14
 */
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    SubjectServiceImpl subjectService;

    /**
     * 院级管理员添加章节
     * @param chapter
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addChapter(Integer facultyId, Chapter chapter) throws JsonProcessingException {
        if(!chapterMapper.addChapter(chapter)){
            return false;
        }
        subjectService.updateSubjectInRedis(facultyId);
        return true;
    }

    /**
     * 院级管理员删除章节
     * @param chapterId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteChapter(Integer facultyId, Integer chapterId) throws JsonProcessingException {
        if(!chapterMapper.deleteChapter(chapterId)){
            return false;
        }
        subjectService.updateSubjectInRedis(facultyId);
        return true;
    }
}
