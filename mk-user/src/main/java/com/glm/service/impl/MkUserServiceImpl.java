package com.glm.service.impl;

import com.glm.entity.ResponseCodeEnum;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.pojo.MkUser;
import com.glm.entity.vo.LoginVO;
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
            return ResponseResult.error("账号密码不匹配");
        }
        return null;
    }

}
