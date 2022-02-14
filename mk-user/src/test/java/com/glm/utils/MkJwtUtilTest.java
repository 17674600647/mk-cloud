package com.glm.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MkJwtUtilTest {
    @Autowired
    MkJwtUtil mkJwtUtil;

    @Test
    void createToken() {
        Map<String, String> maXTp = new HashMap<String, String>();
        maXTp.put("token", "dsadasdas");
        String token = mkJwtUtil.createToken(maXTp);
        System.out.println(token);
        mkJwtUtil.getUserInfoByToken(token);
    }

    @Test
    void getUserInfoByToken() {
    }

    @Test
    void checkJWT() {
    }
}