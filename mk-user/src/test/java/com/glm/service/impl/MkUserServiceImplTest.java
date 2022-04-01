package com.glm.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 15:31
 */

@SpringBootTest
public class MkUserServiceImplTest {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MkJwtUtil mkJwtUtil;

    @Test
    public void JWTTest() {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("uid", Integer.parseInt("123"));
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 15);
            }
        };

        String token = JWTUtil.createToken(map, "1234".getBytes());
        System.out.println(token);
    }

    @Test
    public void JWTParse() {
        String rightToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
                "eyJzdWIiOiIxMjM0NTY3ODkwIiwiYWRtaW4iOnRydWUsIm5hbWUiOiJsb29seSJ9." +
                "U2aQkC2THYV9L0fTN-yBBI7gmo5xhmvMhATtu8v0zEA";

        final JWT jwt = JWTUtil.parseToken(rightToken);

        jwt.getHeader(JWTHeader.TYPE);
        jwt.getPayload("sub");
    }

    @Test
    public void test2() {
        System.out.println((String) redisUtil.get("mkcloud_1044204713@qq.com"));
    }

    @Test
    public void test3() {
        System.out.println(SecureUtil.md5("1234567899"));
    }

    @Test
    public void test4() {
        mkJwtUtil.getUserRoleFromHeader();
    }
}
