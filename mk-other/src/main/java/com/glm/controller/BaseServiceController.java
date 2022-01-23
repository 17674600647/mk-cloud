package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.EmailSendDTO;
import com.glm.handle.ControllerFieldAspect;
import com.glm.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: mk-cloud
 * @description: 基础服务(邮箱验证码)
 * @author: lizhiyong
 * @create: 2022-01-23 20:46
 **/

@RestController
@RequestMapping("/base")
@Api(tags = "其他服务基础服务接口")
@ControllerFieldAspect
public class BaseServiceController {
    @Autowired
    EmailService emailService;

    @PostMapping("/email/send")
    @ApiOperation("发送邮箱验证码接口")
    public ResponseResult emailSend(@RequestBody @Valid EmailSendDTO emailSendDTO, BindingResult bindingResult) {
        return emailService.sendEmail(emailSendDTO.getEmail());
    }

}
