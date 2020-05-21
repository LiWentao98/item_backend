package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.QuestionTypeMapper;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.QuestionType;
import com.item_backend.service.QuestionTypeService;
import com.item_backend.service.SubjectService;
import com.item_backend.utils.JwtTokenUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
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
public class QuestionTypeServiceImpl implements QuestionTypeService {

    @Autowired
    QuestionTypeMapper questionTypeMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    SubjectServiceImpl subjectService;

    /**
     * 院级管理员添加题型
     * @param questionType
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addQuestionType(Integer facultyId, QuestionType questionType) throws JsonProcessingException {
        if(!questionTypeMapper.addQuestionType(questionType)){
            return false;
        }
        subjectService.updateSubjectInRedis(facultyId);
        return true;
    }

    /**
     * 院级管理员删除题型
     * @param questionTypeId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteQuestionType(Integer facultyId, Integer questionTypeId) throws JsonProcessingException {
        if(!questionTypeMapper.deleteQuestionType(questionTypeId)){
            return false;
        }
        subjectService.updateSubjectInRedis(facultyId);
        return true;
    }
}
