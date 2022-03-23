package com.glm.entity.vo;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glm.entity.pojo.MkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: mk-cloud
 * @description: 用户信息对公返回类
 * @author: lizhiyong
 * @create: 2022-03-11 20:48
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfoOutVO implements Serializable {
    private String nickName;
    private String describe;
    private String picUrl;
    private int age;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    public static UserInfoOutVO getInfoFromMkUser(MkUser mkUser) {
        UserInfoOutVO userInfoVO = new UserInfoOutVO();
        userInfoVO.nickName = mkUser.getNickName();
        userInfoVO.describe = mkUser.getDescribe();
        userInfoVO.age = mkUser.getAge();
        userInfoVO.createTime = mkUser.getCreateTime();
        userInfoVO.picUrl = mkUser.getPicUrl();
        return userInfoVO;
    }
}
