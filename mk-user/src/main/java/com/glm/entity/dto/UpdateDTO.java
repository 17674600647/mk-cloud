package com.glm.entity.dto;

import cn.hutool.crypto.SecureUtil;
import com.glm.entity.pojo.MkUser;
import com.glm.service.MkUserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: mk-cloud
 * @description: 用户信息更新用
 * @author: lizhiyong
 * @create: 2022-03-13 16:31
 **/


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {
    private String nickName;
    private Integer age;
    private String describe;
    private String email;
    private String password;
    private String oldPassword;
    private String checkPass;
    private Integer flag;

    public MkUser getMkUser() {
        if (this.flag == 0) {
            return null;
        }
        //修改昵称
        if (this.flag == 1) {
            return new MkUser().setNickName(this.nickName);
        }
        //修改年龄
        if (this.flag == 2) {
            return new MkUser().setAge(this.age);
        }
        //修改邮箱
        if (this.flag == 3) {
            return new MkUser().setEmail(this.email);
        }
        //修改签名
        if (this.flag == 4) {
            return new MkUser().setDescribe(this.describe);
        }
        //修改密码
        if (this.flag == 5) {
            if (!this.password.equals(this.checkPass)) {
                return null;
            }
            return new MkUser().setPassword(SecureUtil.md5(this.password));
        }
        return null;
    }

}
