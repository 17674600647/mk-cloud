package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.vo.LoginVO;
import org.springframework.stereotype.Service;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:47
 */


public interface MkUserService {
    public ResponseResult login(LoginDTO loginDTO);
}
