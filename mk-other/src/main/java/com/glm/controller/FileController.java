package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.exception.ControllerFieldAspect;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    @PostMapping("/pic/upload")
    @ApiOperation("图片上传")
    public ResponseResult picUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        //编写图片上传的业务逻辑方法
        //获取图片名称
        String filename = file.getName();
        //获取图片扩展名
        String ext = filename.substring(filename.lastIndexOf(".")+1);
        System.out.println(filename);
       /* //生成图片名称
        String imgName =UtilTools.getname();//自己写的一个获取字符串的方法作为图片名称
        //生成图片的存放在服务器的路径
        String path = "/imgs/"+imgName + "." + ext;
        //获取服务器的绝对路径进行保存图片
        String url = request.getSession().getServletContext().getRealPath("")+path;*/

        return null;
    }
}
