package com.glm.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.glm.config.exception.MessageException;
import com.glm.entity.FinalString;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.*;
import com.glm.entity.pojo.MkUser;
import com.glm.entity.vo.LoginVO;

import com.glm.entity.vo.UserInfoVO;
import com.glm.feign.MkBaseFeign;
import com.glm.feign.MkOtherFeign;
import com.glm.mapper.MkUserMapper;
import com.glm.service.MkUserService;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.RedisUtil;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

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

    private final String TokenPre = "TOKEN_";
    private final String TOKEN_USER_INFO_ = "token_user_info_";
    private final String TOKEN_USER_INFO_OUT = "token_user_info_out";

    private final int TokenOverTime = 36000;

    @Value("${Email.checkCode}")
    String redisPrefix;

    @Value("${Jwt.key}")
    String JwtKey;

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MkJwtUtil mkjwtUtil;

    @Autowired
    MkOtherFeign mkOtherFeign;

    @Autowired
    MkBaseFeign mkBaseFeign;

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
        mkUser.desensitized();
        Map<String, String> jwtMap = new HashMap<String, String>();
        jwtMap.put(FinalString.USERID, mkUser.getId().toString());
        jwtMap.put("nickName", mkUser.getNickName());
        String token = mkjwtUtil.createToken(jwtMap);
        //用户信息base64位编码保存
        String userInfoBase64 = Base64.encode(JSONUtil.toJsonStr(mkUser));
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setAuthInfo(userInfoBase64);
        //保存到Redis,过期时间设置为1小时
        redisUtil.set(TokenPre + mkUser.getId(), token, TokenOverTime);
        return ResponseResult.success("登录成功", loginVO);
    }

    @Override
    public ResponseResult register(RegisterDTO registerDTO) {
        String checkCode = (String) redisUtil.get(redisPrefix + registerDTO.getEmail());
        log.info("RedisKey:" + redisPrefix + registerDTO.getEmail());
        if (Objects.isNull(checkCode) || checkCode.length() == 0) {
            return ResponseResult.error("请点击发送验证码~");
        }
        if (!checkCode.equals(registerDTO.getCheckCode())) {
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

    @Override
    public ResponseResult verifyToken(AuthDto authDto) {
        String userId = mkjwtUtil.getUserIdByToken(authDto.getToken().trim());
        if (redisUtil.get(TokenPre + userId) != null) {
            //重新设置时间
            redisUtil.expire(TokenPre + userId, TokenOverTime);
            return ResponseResult.success("登陆状态未失效");
        }
        return ResponseResult.error("登陆状态失效");
    }

    @Override
    public ResponseResult getInfo() {
        String idFromHeader = mkjwtUtil.getUserIdFromHeader();
        String jsonInfo = (String) redisUtil.get(TOKEN_USER_INFO_ + idFromHeader);
        if (jsonInfo != null) {
            UserInfoVO redisInfo = JSONUtil.toBean(jsonInfo, UserInfoVO.class);
            return ResponseResult.success("查询成功~", redisInfo);
        }
        MkUser mkUser = userMapper.selectById(Long.valueOf(idFromHeader));
        UserInfoVO fromMkUser = UserInfoVO.getInfoFromMkUser(mkUser);
        //保存用户信息到redis
        redisUtil.cacheData(TOKEN_USER_INFO_ + idFromHeader, fromMkUser,600L);
        return ResponseResult.success("查询成功~", fromMkUser);
    }

    @Override
    public ResponseResult changeUrl(MultipartFile file) {
        ResponseResult responseResult = mkOtherFeign.picUpload(file);
        if (!responseResult.getCode().equals("200")) {
            return ResponseResult.error("头像上传失败" + responseResult.getMessage());
        }
        if (StringUtils.isEmpty(responseResult.getData())) {
            return ResponseResult.error("头像上传失败");
        }
        String id = mkjwtUtil.getUserIdFromHeader();
        UpdateWrapper<MkUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", Long.valueOf(id));
        MkUser mkUser = new MkUser();
        mkUser.setPicUrl(responseResult.getData().toString());
        int update = userMapper.update(mkUser, updateWrapper);
        if (update == 0) {
            return ResponseResult.error("头像上传失败");
        }
        redisUtil.delete(TOKEN_USER_INFO_ + id);
        return ResponseResult.success("上传成功~！");
    }

    @Override
    public ResponseResult updateUserInfoS(UpdateDTO updateDTO) {
        if (updateDTO.getFlag() == 5) {
            String md5OldPassword = SecureUtil.md5(updateDTO.getOldPassword());
            QueryWrapper<MkUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("password").eq("id", Long.valueOf(mkjwtUtil.getUserIdFromHeader()));
            MkUser mkUser = userMapper.selectOne(queryWrapper);
            if (!mkUser.getPassword().equals(md5OldPassword)) {
                return ResponseResult.error("信息更新失败,原密码不正确");
            }
        }
        MkUser mkUser = updateDTO.getMkUser();
        String idFromHeader = mkjwtUtil.getUserIdFromHeader();
        UpdateWrapper<MkUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", Long.valueOf(idFromHeader));
        int update = userMapper.update(mkUser, updateWrapper);
        redisUtil.delete(TOKEN_USER_INFO_ + idFromHeader);
        redisUtil.delete(TOKEN_USER_INFO_OUT + idFromHeader);
        if (update == 0) {
            return ResponseResult.error("信息更新失败");
        }
        return ResponseResult.success("信息成功~！");
    }

    @Override
    public ResponseResult getMkUserInfo(GetOneNoteDTO getNote) {
        try {
            ResponseResult note = mkBaseFeign.getANote(getNote);
            JSONObject jsonObject = JSONUtil.parseObj(note.getData());
            //获取用户ID
            Long userId = (Long) jsonObject.get("userId");
            String jsonInfo = (String) redisUtil.get(TOKEN_USER_INFO_OUT + userId);
            if (jsonInfo != null) {
                UserInfoVO redisInfo = JSONUtil.toBean(jsonInfo, UserInfoVO.class);
                return ResponseResult.success("查询成功~", redisInfo);
            }
            MkUser mkUser = userMapper.selectById(userId);
            UserInfoVO fromMkUser = UserInfoVO.getInfoFromMkUser(mkUser);
            //保存用户信息到redis
            redisUtil.cacheData(TOKEN_USER_INFO_OUT + userId, fromMkUser,600L);
            return ResponseResult.success("查询成功~", fromMkUser);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("查询文章用户数据出错啦~");
        }

    }
}
