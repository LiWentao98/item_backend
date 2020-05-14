package com.item_backend.controller;

import com.baidu.ueditor.ActionEnter;
import com.item_backend.model.dto.UEditorFile;
import com.item_backend.utils.UEditorUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author xiao
 * @Time 2020/5/12
 * @Describe 与前端UEditor交互的Controller，主要用于返回后端UEditor配置给前端
 **/
@Controller
public class UEditorController {
    @Autowired
    private UEditorUploadUtil uEditorUploadUtil;

    //用于给前端UEditor返回后端相应配置文件
    @ResponseBody
    @RequestMapping("/ueditor")
    public UEditorFile uEditorConfig(HttpServletRequest request, HttpServletResponse response, String action, MultipartFile upfile) throws Exception {
        if (action.equals("config")) {
            request.setCharacterEncoding("utf-8");
            response.setHeader("Content-Type", "text/html");
            String uEditorConfigFilePath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "ueditor";
            PrintWriter filterWriter = response.getWriter();
            filterWriter.write(new ActionEnter(request, uEditorConfigFilePath).exec());
            filterWriter.flush();
            filterWriter.close();
        } else if (action.equals("uploadimage")) {
            return uEditorUploadUtil.uploadImage(upfile);
        }
        return null;
    }
}
