package com.glm.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.glm.entity.ExceptionGlobal;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.dto.RegisterDTO;
import com.glm.entity.pojo.MkUser;
import com.glm.entity.vo.LoginVO;
import com.glm.entity.vo.RegisterVO;
import com.glm.mapper.MkUserMapper;
import com.glm.service.MkUserService;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.RedisUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:48
 */
@Service
@Log4j2
public class MkUserServiceImpl implements MkUserService {
    @Autowired
    private MkUserMapper userMapper;

    @Value("${Email.checkCode}")
    String redisPrefix;

    @Value("${Jwt.key}")
    String JwtKey;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MkJwtUtil mkjwtUtil;

    @Override
    public ResponseResult login(LoginDTO loginDTO) {
        Map<String, Object> loginMap = new HashMap<String, Object>();
        if (Validator.isEmail(loginDTO.getAccount())) {
            loginMap.put("email", loginDTO.getAccount());
        } else {
            loginMap.put("username", loginDTO.getAccount());
        }
        loginMap.put("password", SecureUtil.md5(loginDTO.getPassword()));
        List<MkUser> mkUsers = userMapper.selectByMap(loginMap);
        if (Objects.isNull(mkUsers) || mkUsers.size() == 0) {
            return ResponseResult.error("账号/邮箱密码不匹配~");
        }
        MkUser mkUser = mkUsers.get(0);
        Map<String, String> jwtMap = new HashMap<String, String>();
        jwtMap.put("userId", mkUser.getId().toString());
        jwtMap.put("nickName", mkUser.getNickName());
        String token = mkjwtUtil.createToken(jwtMap);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        return ResponseResult.success("登录成功", loginVO);
    }

    @Override
    public ResponseResult register(RegisterDTO registerDTO) {
        String redisKey=redisPrefix + registerDTO.getEmail();
        String checkCode = (String) redisUtil.get(redisKey);
        log.info("RedisKey"+redisPrefix + registerDTO.getEmail());
        if (Objects.isNull(checkCode) || checkCode.length() == 0) {
            return ResponseResult.error("请点击发送验证码~");
        }
        if (checkCode.equals(registerDTO.getCheckCode())) {
            {
                return ResponseResult.error("验证码不正确~");
            }
        }
        MkUser registerMkUser = new MkUser();
        registerMkUser.setUsername(registerDTO.getUsername());
        String md5Password = SecureUtil.md5(registerDTO.getPassword());
        registerMkUser.setPassword(md5Password);
        registerMkUser.setEmail(registerDTO.getEmail());
        //随机昵称
        registerMkUser.setNickName(RandomUtil.randomString(8));
        int insert = 0;
        try {
            insert = userMapper.insert(registerMkUser);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.error("注册失败~,账号或者邮箱存在~");
        }
        if (insert == 1) {
            return ResponseResult.success("注册成功~");
        }
        return ResponseResult.error("注册失败~");

    }

}
