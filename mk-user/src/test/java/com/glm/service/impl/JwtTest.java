package com.glm.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import com.glm.utils.RedisUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-01-24 20:38
 **/

@SpringBootTest(classes = JwtTest.class)
public class JwtTest {
    @Test
    public void test1() {
        System.out.println(new DateTime().offset(DateField.HOUR, 1));
    }


}
