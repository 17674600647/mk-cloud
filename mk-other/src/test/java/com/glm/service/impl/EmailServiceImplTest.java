package com.glm.service.impl;

import com.glm.MkOtherStart;
import com.glm.entity.ResponseResult;
import com.glm.service.EmailService;
import com.glm.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MkOtherStart.class)
public class EmailServiceImplTest {
    @Autowired
    EmailService emailService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendEmail() {
        ResponseResult responseResult = emailService.sendEmail("1044204713@qq.com");
        System.out.println(responseResult);
    }

    @Test
    public void test2() {
        System.out.println((String) redisUtil.get("mkcloud_1044204713@qq.com"));
        System.out.println(redisTemplate.opsForValue().get("mkcloud_1044204713@qq.com"));
    }
}