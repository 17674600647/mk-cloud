package com.glm.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.mail.MailUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.glm.entity.ResponseResult;
import com.glm.service.EmailService;
import com.glm.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @program: mk-cloud
 * @description: 邮件发送服务类]
 * @author: lizhiyong
 * @create: 2022-01-23 21:00
 **/

@Service
@Log4j2
public class EmailServiceImpl implements EmailService {

    @Value("${Email.checkCode}")
    String redisPrefix;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ResponseResult sendEmail(String email) {
        StringBuilder contentBuilder = new StringBuilder();
        String randomCheckCode = RandomUtil.randomNumbers(5);
        contentBuilder.append("<h3>你的验证码为:</h3><h2>")
                .append(randomCheckCode)
                .append("</h2><h3> 验证码5分钟内有效~</h3>");
        String send = MailUtil.send(email, "码克荡云笔记~", contentBuilder.toString(), true);
        log.info("验证码:"+randomCheckCode);
        if (ObjectUtils.isNotNull(send) && send.length() > 0) {
            redisUtil.set(redisPrefix+email,randomCheckCode,300);
            return ResponseResult.success(send);
        }
        return ResponseResult.error("发送失败~");
    }
}
