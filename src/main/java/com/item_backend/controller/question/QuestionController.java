package com.item_backend.controller.question;

import com.item_backend.config.JwtConfig;
import com.item_backend.model.entity.Question;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.QuestionServiceImpl;
import com.item_backend.utils.FormatUtil;
import com.item_backend.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Description:
 * @Author: Mt.Li
 * @Create: 2020-05-12 21:14
 */
@Api(tags = "Question_api", description = "Question_api", basePath = "/question")
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionServiceImpl questionService;

    @Autowired
    FormatUtil formatUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    HttpServletRequest request;

    /**
     * 条件检索试题
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "条件检索试题", notes = "Result：状态码+msg+(data)", httpMethod = "POST")
    @PostMapping({"/search_question/{page}/{showCount}"})
    public Result questionList(@RequestBody Question question, @PathVariable("page") Integer page, @PathVariable("showCount") Integer showCount) {
        if (!formatUtil.checkObjectNull(question)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        PageResult<Question> pageResult = questionService.searchQuestion(question, page, showCount);
        return Result.create(StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 我的试题
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "我的试题", notes = "Result：状态码+msg+(data)", httpMethod = "GET")
    @GetMapping({"/my_questions/{page}/{showCount}"})
    public Result myQuestions(@PathVariable("page") Integer page, @PathVariable("showCount") Integer showCount) {
        PageResult<Question> pageResult = questionService.getMyQuestions(request.getHeader(jwtConfig.getHeader()), page, showCount);
        if (pageResult == null) {
            return Result.create(StatusCode.ACCESSERROR, "无效的token");
        }
        return Result.create(StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 待审试题列表
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "待审试题列表", notes = "Result：状态码+msg+(data)", httpMethod = "GET")
    @GetMapping({"/pending_questions/{page}/{showCount}"})
    public Result pendQuestions(@PathVariable("page") Integer page, @PathVariable("showCount") Integer showCount) {
        PageResult<Question> pageResult = questionService.getPendingQuestions(request.getHeader(jwtConfig.getHeader()), page, showCount);
        if (pageResult == null) {
            return Result.create(StatusCode.ACCESSERROR, "无效的token");
        }
        return Result.create(StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 试题详情
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "试题详情", notes = "Result：状态码+msg+(data)", httpMethod = "GET")
    @GetMapping({"/question_detail/{q_id}"})
    public Result questionDetail(@PathVariable("q_id") Integer q_id) {
        Map map = questionService.getQuestionDetail(q_id);
        if (map.get("msg") != null) {
            return Result.create(StatusCode.OK, map.get("msg").toString());
        }
        return Result.create(StatusCode.OK, "查询成功", map);
    }

    /**
     * 保存试题
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "保存试题", notes = "Result：状态码+msg+(data)", httpMethod = "POST")
    @PostMapping("/save_question")
    public Result saveQuestion(@RequestBody Question question) {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request, "命题教师")) {
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if (!formatUtil.checkObjectNull(question)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if (!questionService.saveQuestion(request.getHeader(jwtConfig.getHeader()), question)) {
            return Result.create(StatusCode.ERROR, "保存失败");
        }
        return Result.create(StatusCode.OK, "保存成功");
    }

    /**
     * 审核试题
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "审核试题", notes = "Result：状态码+msg+(data)", httpMethod = "POST")
    @PostMapping("/examine_question")
    public Result examineQuestion(@RequestBody Question question) {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request, "审核教师")) {
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if (!formatUtil.checkObjectNull(question)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if (!questionService.examineQuestion(request.getHeader(jwtConfig.getHeader()), question)) {
            return Result.create(StatusCode.ERROR, "审核失败");
        }
        return Result.create(StatusCode.OK, "审核成功");
    }

}
