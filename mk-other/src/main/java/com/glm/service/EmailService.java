package com.glm.service;

import com.glm.entity.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @program: mk-cloud
 * @description: 邮箱验证码服务
 * @author: lizhiyong
 * @create: 2022-01-23 20:50
 **/


public interface EmailService {
    //发送邮箱验证码
    public ResponseResult sendEmail(String email);
}
