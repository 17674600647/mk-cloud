package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.handle.ControllerFieldAspect;
import com.glm.service.MkUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/14 17:23
 */
@RestController
@RequestMapping("/basic/services")
@Api(tags = "用户基础服务")
@ControllerFieldAspect
public class UserBasicServicesController {
    @Autowired
    MkUserService mkUserService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseResult login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult) {
       if (bindingResult.hasErrors()){
           List<FieldError> fieldErrors = bindingResult.getFieldErrors();
           StringBuilder errorStrBuild = new StringBuilder();
           for (FieldError fieldError : fieldErrors) {
               errorStrBuild.append(fieldError.getDefaultMessage());
           }
           return ResponseResult.error(errorStrBuild.toString());
       }
        return mkUserService.login(loginDTO);
    }

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseResult register() {
        return null;
    }
}
