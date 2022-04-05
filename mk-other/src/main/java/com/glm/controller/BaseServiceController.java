package com.glm.controller;

import com.glm.config.exception.BaseException;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.EmailSendDTO;
import com.glm.entity.dto.QueryLogsPageDTO;
import com.glm.exception.ControllerFieldAspect;
import com.glm.service.EmailService;
import com.glm.service.LogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

public class BaseServiceController {
    @Autowired
    EmailService emailService;

    @Autowired
    LogsService logsService;

    @PostMapping("/email/send")
    @ApiOperation("发送邮箱验证码接口")
    public ResponseResult emailSend(@RequestBody @Valid EmailSendDTO emailSendDTO) {
        return emailService.sendEmail(emailSendDTO.getEmail());
    }

    @PostMapping("/query/logs")
    @ApiOperation("查询日志接口")
    public ResponseResult queryLogs(@RequestBody @Valid QueryLogsPageDTO queryLogsPage) {
        return logsService.queryLogsByLevel(queryLogsPage);
    }

}
