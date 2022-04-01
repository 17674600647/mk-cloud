package com.glm.utils;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.jwt.JWT;
import com.glm.config.exception.MessageException;
import com.glm.entity.constant.StringConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

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
    final String TOKEN_USERINFO = "UserInfoMap";

    public String createToken(Map map) {
        // 密钥
        byte[] key = JwtKey.getBytes();
        // 使用HuTool工具的 HS265(HmacSHA256)算法
        return JWT.create()
                .setPayload(TOKEN_USERINFO, map)
                .setKey(key)
                .setExpiresAt(new DateTime().offset(DateField.DAY_OF_WEEK, 1))
                .sign();
    }

    //获取用户信息
    public Map<String, String> getUserInfoByToken(String token) {
        JWT jwt = JWT.of(token);
        return (Map<String, String>) jwt.getPayload(TOKEN_USERINFO);
    }

    //获取用户ID
    public String getUserIdByToken(String token) {
        Map<String, String> userIdMap = getUserInfoByToken(token);
        if (userIdMap!=null&&userIdMap.get(StringConstant.USERID) != null) {
            return userIdMap.get(StringConstant.USERID);
        }
        throw new MessageException("无法获取UerId出错~");
    }

    public Integer getUserRoleByToken(String token) {
        Map<String, String> userIdMap = getUserInfoByToken(token);
        if (userIdMap!=null&&userIdMap.get(StringConstant.USER_AUTH) != null) {
            return Integer.valueOf(userIdMap.get(StringConstant.USER_AUTH));
        }
        throw new MessageException("权限出错~");
    }


    //验证JWT是否有效
    public boolean checkJWT(String token) {
        // 默认验证HS265的算法
        return JWT.of(token).setKey(JwtKey.getBytes()).verify();
    }

    //从请求头中获取
    public String getUserIdFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        if (token != null) {
            return getUserIdByToken(token);
        }
        return null;
    }

    //从请求头中获取
    public Integer getUserRoleFromHeader() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        if (token != null) {
            return getUserRoleByToken(token);
        }
        return null;
    }
}
