package com.item_backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.item_backend.model.dto.SubjectDto;
import com.item_backend.model.entity.Chapter;
import com.item_backend.model.entity.QuestionType;
import com.item_backend.model.entity.Subject;
import com.item_backend.model.pojo.PageResult;
import com.item_backend.model.pojo.Result;
import com.item_backend.model.pojo.StatusCode;
import com.item_backend.service.impl.ChapterServiceImpl;
import com.item_backend.service.impl.QuestionTypeServiceImpl;
import com.item_backend.service.impl.SubjectServiceImpl;
import com.item_backend.service.impl.UserServiceImpl;
import com.item_backend.utils.FormatUtil;
import com.item_backend.utils.JwtTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Description: 学科控制层
 * @Author: Mt.Li
 * @Create: 2020-05-21 17:38
 */

@Api(tags = "Subject_api", description = "Subject_api", basePath = "/subject")
@RestController
@RequestMapping("/subject")
public class SubjectController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    SubjectServiceImpl subjectService;

    @Autowired
    ChapterServiceImpl chapterService;

    @Autowired
    QuestionTypeServiceImpl questionTypeService;

    @Autowired
    FormatUtil formatUtil;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    HttpServletRequest request;

    /**
     * 院级管理员查询学科列表
     * @param page
     * @param showCount
     * @return
     */
    @ApiOperation(value = "院级管理员查询学科列表", notes = "页数+显示数量")
    @GetMapping("/subject_list/{facultyId}/{page}/{showCount}")
    public Result subjectList(@PathVariable(value = "facultyId") Integer facultyId,
                              @PathVariable(value = "page") Integer page,
                              @PathVariable(value = "showCount") Integer showCount){
        if (!formatUtil.checkPositive(page, showCount)) {
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        // 判断是否拥有权限
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        Integer count = subjectService.getSubjectCount(facultyId);
        List<SubjectDto> subjectDto;
        try {
            subjectDto = subjectService.SearchSubjectList(facultyId, page, showCount);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.create(StatusCode.SERVICEERROR, "服务异常");
        }
        if(count == 0 || subjectDto == null){
            return Result.create(StatusCode.OK, "查询结果为空");
        }
        // 将结果封装到分页结果类
        PageResult<SubjectDto> pageResult = new PageResult(count, subjectDto);
        return Result.create(StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 院级管理员添加学科
     * @param subject
     * @return
     */
    @PostMapping("/add_subject/{facultyId}")
    public Result addSubject(@PathVariable(value = "facultyId") Integer facultyId,
                             @RequestBody Subject subject) throws JsonProcessingException {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if(!subjectService.addSubject(facultyId,subject)){
            return Result.create(StatusCode.ERROR,"添加失败");
        }
        return Result.create(StatusCode.OK,"添加成功");
    }

    /**
     * 院级管理员删除学科
     * @param subjectId
     * @return
     */
    @DeleteMapping("/delete_subject/{facultyId}/{subjectId}")
    public Result deleteSubject(@PathVariable(value = "facultyId") Integer facultyId,
                                @PathVariable(value = "subjectId") Integer subjectId) throws JsonProcessingException {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        // 判断数据是否为空
        if(!formatUtil.checkObjectNull(subjectId)){
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if(!subjectService.deleteSubjectById(facultyId,subjectId)){
            return Result.create(StatusCode.ERROR,"删除失败");
        }
        return Result.create(StatusCode.OK,"删除成功");

    }

    /**
     * 院级管理员添加题型
     * @param questionType
     * @return
     */
    @PostMapping("/add_q_type/{facultyId}")
    public Result addQuestionType(@PathVariable(value = "facultyId") Integer facultyId,
                                  @RequestBody QuestionType questionType) throws JsonProcessingException {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if(!questionTypeService.addQuestionType(facultyId,questionType)){
            return Result.create(StatusCode.ERROR,"添加失败");
        }
        return Result.create(StatusCode.OK,"添加成功");
    }

    /**
     * 院级管理员删除题型
     * @param qTypeId
     * @return
     */
    @DeleteMapping("/delete_q_type/{facultyId}/{qTypeId}")
    public Result deleteQType(@PathVariable(value = "facultyId") Integer facultyId,
                              @PathVariable(value = "qTypeId") Integer qTypeId) throws JsonProcessingException {
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        // 判断数据是否为空
        if(!formatUtil.checkObjectNull(qTypeId)){
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if(!questionTypeService.deleteQuestionType(facultyId,qTypeId)){
            return Result.create(StatusCode.ERROR,"删除失败");
        }

        return Result.create(StatusCode.OK,"删除成功");
    }

    /**
     * 院级管理员添加章节
     * @param chapter
     * @return
     */
    @PostMapping("/add_chapter/{facultyId}")
    public Result addChapter(@PathVariable(value = "facultyId") Integer facultyId,
                             @RequestBody Chapter chapter) throws JsonProcessingException {
        // 判断权限
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        if(!chapterService.addChapter(facultyId,chapter)){
            return Result.create(StatusCode.ERROR,"添加失败");
        }
        return Result.create(StatusCode.OK,"添加成功");
    }

    /**
     * 院级管理员删除章节
     * @param chapterId
     * @return
     */
    @DeleteMapping("/delete_chapter/{facultyId}/{chapterId}")
    public Result deleteChapter(@PathVariable(value = "facultyId") Integer facultyId,
                                @PathVariable(value = "chapterId") Integer chapterId) throws JsonProcessingException {
        if (!jwtTokenUtil.checkUserType(request,"院级管理员")){
            return Result.create(StatusCode.ACCESSERROR, "无权限");
        }
        // 判断数据是否为空
        if(!formatUtil.checkObjectNull(chapterId)){
            return Result.create(StatusCode.ERROR, "参数错误");
        }
        if(!chapterService.deleteChapter(facultyId,chapterId)){
            return Result.create(StatusCode.ERROR,"删除失败");
        }
        return Result.create(StatusCode.OK,"删除成功");
    }
}
