package com.item_backend.utils;

import com.item_backend.model.dto.UEditorFile;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author xiao
 * @Time 2020/5/12
 * @Describe UEditor上传文件工具类
 **/

@Component
public class UEditorUploadUtil {
    private String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

    public UEditorFile uploadImage(MultipartFile file) throws IOException {
        //获取文件名
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        //获取系统时间组成文件保存路径
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = "" + dateFormat.format(new Date());
        //UEditor的config.json规定的返回路径格式
        String returnPath = "/api/image/ueditor/" + time + "/" + fileName;
        //保存路径
        String saveDir = path + "static/image/ueditor/" + time + "/";
        File saveDirFile = new File(saveDir);
        //保存路径+文件名
        File saveFile = new File(saveDir + fileName);
        //没有保存路径时创建该文件夹
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        //将文件写入
        if (file.getSize() > 0) {
            InputStream ins = file.getInputStream();
            try {
                OutputStream os = new FileOutputStream(saveFile);
                int bytesRead;
                byte[] buffer = new byte[8912];
                while ((bytesRead = ins.read(buffer, 0, 8912)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
                os.close();
                ins.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        UEditorFile uEditorFile = new UEditorFile();
        uEditorFile.setState("SUCCESS");
        //用于被前台访问的URL
        uEditorFile.setUrl(returnPath);
        uEditorFile.setTitle(fileName);
        uEditorFile.setOriginal(fileName);
        return uEditorFile;
    }
}
