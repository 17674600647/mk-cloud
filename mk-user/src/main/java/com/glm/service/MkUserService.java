package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.*;
import com.glm.entity.vo.LoginVO;
import com.glm.entity.vo.RegisterVO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:47
 */


public interface MkUserService {
    /**
     * 登录
     * @param loginDTO
     * @return
     */
    public ResponseResult<LoginVO> login(LoginDTO loginDTO);

    public ResponseResult register(RegisterDTO loginDTO);

    public ResponseResult verifyToken(AuthDto author);

    public ResponseResult getInfo();

    public ResponseResult getInfoById(String userId);

    public ResponseResult changeUrl(MultipartFile file);

    public ResponseResult updateUserInfoS(UpdateDTO updateDTO);

    public ResponseResult getMkUserInfo(GetOneNoteDTO getNote);

    public ResponseResult getUserRole(String token);

    public ResponseResult getAllUsersByStatusAndKeyWords(UserPageByStatusDTO userPageByStatus);

    public ResponseResult updateUserStatus(UpdateUserStatesDTO updateUserStatesDTO);

    public ResponseResult signOut();

}
