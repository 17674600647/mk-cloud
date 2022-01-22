package com.glm.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.glm.entity.ExceptionGlobal;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.dto.RegisterDTO;
import com.glm.entity.pojo.MkUser;
import com.glm.entity.vo.RegisterVO;
import com.glm.mapper.MkUserMapper;
import com.glm.service.MkUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MkUserServiceImpl implements MkUserService {
    @Autowired
    private MkUserMapper userMapper;

    @Override
    public ResponseResult login(LoginDTO loginDTO) {
        Map<String, Object> loginMap = new HashMap<String, Object>();
        loginMap.put("username", loginDTO.getUsername());
        loginMap.put("password", loginDTO.getPassword());
        List<MkUser> mkUsers = userMapper.selectByMap(loginMap);
        if (Objects.isNull(mkUsers) || mkUsers.size() == 0) {
            throw new RuntimeException("账号密码不匹配");
        }
        return null;
    }

    @Override
    public ResponseResult register(RegisterDTO registerDTO) {
        MkUser registerMkUser = new MkUser();
        registerMkUser.setUsername(registerDTO.getUsername());
        String md5Password = SecureUtil.md5(registerDTO.getPassword());
        registerMkUser.setPassword(md5Password);
        registerMkUser.setEmail(registerDTO.getEmail());
        //随机昵称
        registerMkUser.setNickName(RandomUtil.randomString(8));
        int insert = 0 ;
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
