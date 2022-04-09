package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.exception.ControllerFieldAspect;
import com.glm.service.AliOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: mk-cloud
 * @description: 文件操作controller类
 * @author: lizhiyong
 * @create: 2022-02-12 17:42
 **/

@RestController
@RequestMapping("/file")
@Api(tags = "文件操作controller接口")
public class FileController {
    @Autowired
    AliOssService aliOssService;

    @PostMapping("/pic/upload")
    @ApiOperation("图片上传")
    public ResponseResult picUpload(@RequestPart("file") MultipartFile file) {
        String picUrl = aliOssService.upload(file);
        return ResponseResult.success("上传成功", picUrl);
    }
}
