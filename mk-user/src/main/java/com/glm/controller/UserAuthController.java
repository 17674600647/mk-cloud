package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.AuthDto;
import com.glm.entity.dto.LoginDTO;
import com.glm.exception.ControllerFieldAspect;
import com.glm.service.MkUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: mk-cloud
 * @description: 用户身份验证
 * @author: lizhiyong
 * @create: 2022-01-23 20:26
 **/

@RestController
@RequestMapping("/auth")
@Api(tags = "用户身份验证接口")
public class UserAuthController {
    @Autowired
    MkUserService mkUserService;

    @RequestMapping("/tokenCheck")
    @ApiOperation("用户Token身份验证")
    public ResponseResult verifyToken(@RequestBody @Valid AuthDto authdto) {
        return mkUserService.verifyToken(authdto);
    }

    @RequestMapping("/rpc/tokenCheck")
    @ApiOperation("用户Token身份验证,RPC接口")
    public String verifyTokenRPC(@RequestBody String token) {
        AuthDto authDto = new AuthDto();
        authDto.setToken(token);
        return mkUserService.verifyToken(authDto).getCode();
    }

    @RequestMapping("/get/user/role")
    @ApiOperation("用户Token身份验证,RPC接口")
    public ResponseResult getUserRole(@RequestBody(required = false)String token) {
        return mkUserService.getUserRole(token);
    }


}
