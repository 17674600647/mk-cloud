package com.glm.entity.vo;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glm.entity.pojo.MkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: mk-cloud
 * @description: 用户信息返回类
 * @author: lizhiyong
 * @create: 2022-03-11 20:48
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoVO implements Serializable {
    private String nickName;
    private String describe;
    private String picUrl;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String email;

    //信息脱敏
    public void desensitized() {
        this.email = DesensitizedUtil.email(this.email);
    }

    public static UserInfoVO getInfoFromMkUser(MkUser mkUser) {
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.email = mkUser.getEmail();
        userInfoVO.desensitized();
        userInfoVO.nickName = mkUser.getNickName();
        userInfoVO.describe = mkUser.getDescribe();
        userInfoVO.age = mkUser.getAge();
        userInfoVO.createTime = mkUser.getCreateTime();
        userInfoVO.picUrl = mkUser.getPicUrl();
        return userInfoVO;
    }
}
