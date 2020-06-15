package com.item_backend.controller.examPaper;

import com.item_backend.config.JwtConfig;
import com.item_backend.model.dto.ExamPaperDto;
import com.item_backend.model.entity.ExamPaper;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.ExamPaperServiceImpl;
import com.item_backend.utils.FormatUtil;
import com.item_backend.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author xiao
 * @Time 2020/6/14
 * @Description TODO
 **/
@Api(tags = "Exam_paper_api", description = "Exam_paper_api", basePath = "/exam_paper")
@RestController
@RequestMapping("/exam_paper")
public class ExamPaperController {
    @Autowired
    ExamPaperServiceImpl examPaperService;

    @Autowired
    FormatUtil formatUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    HttpServletRequest request;

    /**
     * 我的试卷
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "我的试卷", notes = "Result：状态码+msg+(data)", httpMethod = "GET")
    @GetMapping({"/my_exam_papers/{page}/{showCount}"})
    public Result myExamPapers(@PathVariable("page") Integer page, @PathVariable("showCount") Integer showCount) {
        PageResult<ExamPaper> pageResult = examPaperService.getMyExamPapers(request.getHeader(jwtConfig.getHeader()), page, showCount);
        if (pageResult == null) {
            return Result.create(StatusCode.ACCESSERROR, "无效的token");
        }
        return Result.create(StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 试卷详情
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "试卷详情", notes = "Result：状态码+msg+(data)", httpMethod = "GET")
    @GetMapping({"/exam_paper_detail/{e_id}"})
    public Result questionDetail(@PathVariable("e_id") Integer e_id) {
        Map map = examPaperService.getExamPaperDetail(e_id);
        if (map.get("msg") != null) {
            return Result.create(StatusCode.OK, map.get("msg").toString());
        }
        return Result.create(StatusCode.OK, "查询成功", map);
    }

    /**
     * 手动组卷
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "手动组卷", notes = "Result：状态码+msg+(data)", httpMethod = "POST")
    @PostMapping("/save_exam_paper")
    public Result saveQuestion(@RequestBody ExamPaperDto examPaperDto) {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request, "命题教师")) {
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if (!formatUtil.checkObjectNull(examPaperDto)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if (!examPaperService.saveExamPaper(request.getHeader(jwtConfig.getHeader()), examPaperDto)) {
            return Result.create(StatusCode.ERROR, "保存失败");
        }
        return Result.create(StatusCode.OK, "保存成功");
    }

    /**
     * 删除试卷
     *
     * @param
     * @return Result：状态码+msg+(data)
     * @Author xiao
     */
    @ApiOperation(value = "删除试卷", notes = "Result：状态码+msg+(data)", httpMethod = "DELETE")
    @DeleteMapping("/delete_exam_paper/{e_id}")
    public Result deleteMajor(@PathVariable("e_id") Integer e_id) {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request, "命题教师")) {
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        // 判断数据是否为空
        if (!formatUtil.checkObjectNull(e_id)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if (!examPaperService.deleteExamPaper(request.getHeader(jwtConfig.getHeader()), e_id)) {
            return Result.create(StatusCode.ERROR, "删除失败");
        }
        return Result.create(StatusCode.OK, "删除成功");
    }

}
