package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.LoginDTO;
import com.glm.entity.dto.QueryUserInfoByIdDTO;
import com.glm.entity.dto.UpdateUserStatesDTO;
import com.glm.entity.dto.UserPageByStatusDTO;
import com.glm.service.MkUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
@Api(tags = "用户身份验证接口")
public class UserManagerController {
    @Autowired
    MkUserService mkUserService;

    @PostMapping("/get/all/users")
    @ApiOperation("获取所有用户")
    public ResponseResult getAllUserByStatus(@RequestBody @Validated UserPageByStatusDTO userPageByStatus) {
        return mkUserService.getAllUsersByStatusAndKeyWords(userPageByStatus);
    }

    @PostMapping("/update/user/status")
    @ApiOperation("更新用户状态")
    public ResponseResult updateStatusByUserId(@RequestBody @Validated UpdateUserStatesDTO queryUserInfoByIdDTO) {
        return mkUserService.updateUserStatus(queryUserInfoByIdDTO);
    }
}
