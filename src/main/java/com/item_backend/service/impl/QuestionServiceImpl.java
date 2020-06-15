package com.item_backend.service.impl;

import com.alibaba.fastjson.JSON;
import com.item_backend.config.JwtConfig;
import com.item_backend.config.RedisConfig;
import com.item_backend.mapper.QuestionMapper;
import com.item_backend.mapper.UserMapper;
import com.item_backend.model.dto.QuestionDto;
import com.item_backend.model.dto.UserDto;
import com.item_backend.model.entity.Question;
import com.item_backend.model.entity.User;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.service.QuestionService;
import com.item_backend.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xiao
 * @Time 2020/6/11
 * @Description TODO
 **/
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 条件检索试题
     *
     * @param question
     * @param page
     * @param showCount
     * @return
     */
    @Override
    public PageResult<Question> searchQuestion(Question question, Integer page, Integer showCount) {
        List<Question> questionList = new ArrayList<>();
        if (question.getQ_id() == null) {
            questionList = questionMapper.searchQuestionsByProperties(question, (page - 1) * showCount, showCount);
            for (int i = 0; i < questionList.size(); i++) {
                questionList.get(i).setQ_content(HtmlUtils.htmlUnescape(questionList.get(i).getQ_content()));
            }
            return new PageResult<>(questionMapper.getQuestionCountByProperties(question), questionList);
        } else {
            Question q = questionMapper.searchQuestionByQId(question.getQ_id());
            if (q == null) {
                return new PageResult<>(0, new ArrayList<>());
            }
            q.setQ_content(HtmlUtils.htmlUnescape(q.getQ_content()));
            questionList.add(q);
            return new PageResult<>(1, questionList);
        }
    }

    /**
     * 获取我的试题
     *
     * @param token
     * @param page
     * @param showCount
     * @return
     */
    @Override
    public PageResult<Question> getMyQuestions(String token, Integer page, Integer showCount) {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        List<Question> questionList;
        if (u_id == null) {
            return null;
        }
        questionList = questionMapper.searchQuestionsByUId(u_id, (page - 1) * showCount, showCount);
        for (int i = 0; i < questionList.size(); i++) {
            questionList.get(i).setQ_content(HtmlUtils.htmlUnescape(questionList.get(i).getQ_content()));
        }
        return new PageResult<>(questionMapper.getQuestionCountByUId(u_id), questionList);
    }

    /**
     * 获取待审试题列表
     *
     * @param token
     * @param page
     * @param showCount
     * @return
     */
    @Override
    public PageResult<Question> getPendingQuestions(String token, Integer page, Integer showCount) {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        List<Question> questionList;
        if (u_id == null) {
            return null;
        }
        String userDtoJSON = redisTemplate.opsForValue().get(RedisConfig.REDIS_USER_MESSAGE + u_id);
        UserDto userDto = JSON.parseObject(userDtoJSON, UserDto.class);
        questionList = questionMapper.searchPendingQuestionsByOperateSubject(userDto.getUser().getOperate_subject(), (page - 1) * showCount, showCount);
        for (int i = 0; i < questionList.size(); i++) {
            questionList.get(i).setQ_content(HtmlUtils.htmlUnescape(questionList.get(i).getQ_content()));
        }
        return new PageResult<>(questionMapper.getPendingQuestionsCountByOperateSubject(userDto.getUser().getOperate_subject()), questionList);
    }

    /**
     * 获取试题详情
     *
     * @param q_id
     * @return
     */
    @Override
    public Map getQuestionDetail(Integer q_id) {
        Map<String, Object> map = new HashMap<>();
        QuestionDto questionDto = new QuestionDto();
        Question question = questionMapper.searchQuestionByQId(q_id);
        if (question == null) {
            map.put("msg", "查询结果为空");
            return map;
        }
        User user = userMapper.searchUserByUId(question.getU_id());
        questionDto.setQuestion(question);
        questionDto.setUser(user);
        map.put("questionDetail", questionDto);
        return map;
    }

    /**
     * 保存试题
     *
     * @param
     * @return Boolean
     * @Author xiao
     */
    @Override
    public Boolean saveQuestion(String token, Question question) {
        Integer u_id = jwtTokenUtil.getUIDFromToken(token.substring(jwtConfig.getPrefix().length()));
        // 判断u_id的值
        if (u_id == null) {
            return false;
        }
        question.setU_id(u_id);
        question.setQ_content(HtmlUtils.htmlEscape(question.getQ_content()));
        if (question.getQ_id() == null) {
            if (questionMapper.addQuestion(question) <= 0) {
                return false;
            }
        } else {
            if (questionMapper.updateQuestion(question) <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 审核试题
     *
     * @param token
     * @param question
     * @return
     */
    @Override
    public Boolean examineQuestion(String token, Question question) {
        if (questionMapper.changeQuestionState(question.getQ_id(), question.getQ_state()) <= 0) {
            return false;
        }
        return true;
    }
}
