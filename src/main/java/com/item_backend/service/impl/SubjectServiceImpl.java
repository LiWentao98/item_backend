package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.ChapterMapper;
import com.item_backend.mapper.MajorMapper;
import com.item_backend.mapper.QuestionTypeMapper;
import com.item_backend.mapper.SubjectMapper;
import com.item_backend.model.dto.SubjectDto;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.Major;
import com.item_backend.model.entity.Subject;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.SubjectService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-18 22:14
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    SubjectMapper subjectMapper;

    @Autowired
    MajorMapper majorMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    QuestionTypeMapper questionTypeMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Autowired
    HttpServletRequest request;

    /**
     * 获取学院学科数量
     * @param facultyId
     * @return
     */
    @Override
    public Integer getSubjectCount(Integer facultyId) {
        return subjectMapper.getSubjectCount(facultyId);
    }

    /**
     * 查询学科信息列表（包含章节、题型）
     * @param page
     * @param showCount
     * @return
     * @throws JsonProcessingException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<SubjectDto> SearchSubjectList(Integer facultyId, Integer page, Integer showCount) throws JsonProcessingException {
        // 先查询缓存中是否存在
        if(!redisTemplate.hasKey(RedisConfig.REDIS_SUBJECT + facultyId)){
            // 缓存中不存在，先查询所有的学科信息放入redis中
            updateSubjectInRedis(facultyId);
        }
        // redis中已经存在，直接获取
        String subjectMessage = redisTemplate.opsForValue().get(RedisConfig.REDIS_SUBJECT + facultyId);
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, SubjectDto.class);
        List<SubjectDto> subjectDtos = objectMapper.readValue(subjectMessage, javaType);
        subjectDtos = subjectDtos.size() < (page-1)*showCount+showCount
                ? subjectDtos.subList((page-1)*showCount,subjectDtos.size()) : subjectDtos.subList((page-1)*showCount,(page-1)*showCount+showCount);
        return subjectDtos;
    }

    /**
     * 添加学科
     * @param subject
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean addSubject(Integer facultyId, Subject subject) throws JsonProcessingException {
        if(!subjectMapper.addSubject(subject)){
            return false;
        }
        updateSubjectInRedis(facultyId);
        return true;
    }

    /**
     * 通过学科id删除学科
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public Boolean deleteSubjectById(Integer facultyId, Integer id) throws JsonProcessingException {
        if(!subjectMapper.deleteSubjectById(id)){
            return false;
        }
        updateSubjectInRedis(facultyId);
        return true;
    }

    /**
     * 更新redis中的学科信息
     * @param facultyId
     * @throws JsonProcessingException
     */
    @Override
    public void updateSubjectInRedis(Integer facultyId) throws JsonProcessingException {
        if(redisTemplate.hasKey(RedisConfig.REDIS_SUBJECT + facultyId)){
            redisTemplate.delete(RedisConfig.REDIS_SUBJECT + facultyId);
        }
        // System.out.println(getSubjectCount(facultyId));
        List<Subject> subjectList = subjectMapper.searchSubjectList(0, getSubjectCount(facultyId));
        Iterator<Subject> iter = subjectList.iterator();
        List<SubjectDto> subjectDtoList = new ArrayList<>();
        while(iter.hasNext()){
            Subject s = iter.next();
            SubjectDto subjectDto = new SubjectDto();
            subjectDto.setSubject(s);
            subjectDto.setMajor(majorMapper.searchMajorById(s.getMajor_id()));
            subjectDto.setCharacters(chapterMapper.searchChapterBySubjectId(s.getSubject_id()));
            subjectDto.setQuestionTypes(questionTypeMapper.searchQuestionTypeByMajorId(s.getSubject_id()));
            subjectDtoList.add(subjectDto);
        }
        redisTemplate.opsForValue().set(RedisConfig.REDIS_SUBJECT + facultyId, objectMapper.writeValueAsString(subjectDtoList));
    }
}
