package com.glm.controller;

import com.glm.config.exception.TestException;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.dto.RegisterDTO;
import com.glm.exception.ControllerFieldAspect;
import com.glm.feign.MkOtherFeign;
import com.glm.service.MkUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/14 17:23
 */
@RestController
@RequestMapping("/base")
@Api(tags = "用户基础服务")
@Log4j2
public class UserBasicServicesController {
    @Autowired
    MkUserService mkUserService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody @Validated LoginDTO loginDTO) {
        return mkUserService.login(loginDTO);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseResult register(@RequestBody @Valid RegisterDTO registerDTO) {
        log.info(registerDTO);
        return mkUserService.register(registerDTO);
    }

    @PostMapping("/get/info")
    @ApiOperation("用户注册")
    public ResponseResult getInfo() {
        return mkUserService.getInfo();
    }

    @PostMapping("/change/picUrl")
    @ApiOperation("修改用户头像")
    public ResponseResult changeUrl(@RequestParam MultipartFile file) {
        return mkUserService.changeUrl(file);
    }
}
