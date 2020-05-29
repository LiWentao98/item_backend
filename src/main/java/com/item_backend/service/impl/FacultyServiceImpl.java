package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.item_backend.config.JwtConfig;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.FacultyMapper;
import com.item_backend.mapper.SchoolMapper;
import com.item_backend.model.dto.FacultyDto;
import com.item_backend.model.entity.Faculty;
import com.item_backend.model.entity.School;
import com.item_backend.service.FacultyService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author xiao
 * @Time 2020/5/28
 * @Description TODO
 **/
@Service
public class FacultyServiceImpl implements FacultyService {

    @Autowired
    FacultyMapper facultyMapper;

    @Autowired
    SchoolMapper schoolMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 根据学校名查询院系列表
     *
     * @param
     * @return List
     * @Author xiao
     */
    @Override
    public List<FacultyDto> searchFacultyListBySchoolName(String schoolName, Integer page, Integer showCount) throws JsonProcessingException {
        // 先查询缓存中是否存在
        if (!redisTemplate.hasKey(RedisConfig.REDIS_FACULTY + schoolName)) {
            // 缓存中不存在，先查询所有的学科信息放入redis中
            updateFacultyInRedis(schoolName);
        }
        // redis中已经存在，直接获取
        String facultyJSON = redisTemplate.opsForValue().get(RedisConfig.REDIS_FACULTY + schoolName);
        List<FacultyDto> facultyDtoList = JSON.parseObject(facultyJSON, ArrayList.class);
        facultyDtoList = facultyDtoList.size() < page * showCount ?
                facultyDtoList.subList((facultyDtoList.size() / showCount) * showCount, facultyDtoList.size()) :
                facultyDtoList.subList((page - 1) * showCount, page * showCount);
        return facultyDtoList;
    }

    /**
     * 添加院系
     *
     * @param
     * @return Boolean
     * @Author xiao
     */
    @Override
    public Boolean addFaculty(String token, Faculty faculty) throws JsonProcessingException {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        faculty.setU_id(u_id);
        if (facultyMapper.addFaculty(faculty) <= 0) {
            return false;
        }
        updateFacultyInRedis(faculty.getSchool());
        return true;
    }

    /**
     * 根据院系id删除院系
     *
     * @param
     * @return Boolean
     * @Author xiao
     */
    @Override
    public Boolean deleteFacultyByFacultyId(String schoolName, Integer faculty_id) throws JsonProcessingException {
        if (facultyMapper.deleteFacultyByFacultyId(faculty_id) <= 0) {
            return false;
        }
        updateFacultyInRedis(schoolName);
        return true;
    }

    /**
     * 更新redis中的院系信息
     *
     * @param schoolName
     * @throws JsonProcessingException
     */
    @Override
    public void updateFacultyInRedis(String schoolName) throws JsonProcessingException {
        List<Faculty> facultyList = facultyMapper.searchFacultyBySchoolName(schoolName);
        if (facultyList.size() <= 0) return;
        School school = schoolMapper.searchSchoolBySchoolName(schoolName);
        Iterator<Faculty> iter = facultyList.iterator();
        List<FacultyDto> facultyDtoList = new ArrayList<>();
        while (iter.hasNext()) {
            Faculty f = iter.next();
            FacultyDto facultyDto = new FacultyDto();
            facultyDto.setFaculty(f);
            facultyDto.setSchool(school);
            facultyDtoList.add(facultyDto);
        }
        redisTemplate.opsForValue().set(RedisConfig.REDIS_FACULTY + schoolName, objectMapper.writeValueAsString(facultyDtoList));
    }
}
