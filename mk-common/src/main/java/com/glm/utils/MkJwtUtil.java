package com.glm.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: mk-cloud
 * @description: Jwt工具类
 * @author: lizhiyong
 * @create: 2022-01-24 20:01
 **/

@Component
public class MkJwtUtil {
    @Value("${Jwt.key}")
    String JwtKey;

    public String createToken(Map map) {
        // 密钥
        byte[] key = JwtKey.getBytes();
        // 使用HuTool工具的 HS265(HmacSHA256)算法
        return JWT.create()
                .setPayload("UserInfoMap", map)
                .setKey(key)
                .setExpiresAt(new DateTime().offset(DateField.DAY_OF_WEEK, 1))
                .sign();
    }

    public Map parseToken(String token) {
        // 密钥
        byte[] key = JwtKey.getBytes();

        JWTValidator.of(token).validateDate();
        return null;
    }

    //验证JWT是否有效
    public boolean checkJWT(String token) {
        // 默认验证HS265的算法
        return JWT.of(token).setKey(JwtKey.getBytes()).verify();
    }

    //验证是否还具有活性
    public Boolean JwtActive(String token) {
        try {
         JWTValidator.of(token).validateDate(DateUtil.date(), 600);
        } catch (ValidateException e) {
            return false;
        }
        return true;
    }

}
