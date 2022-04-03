package com.glm.entity.pojo;

import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mk_user")
@Accessors(chain = true)
public class MkUser {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic(value = "deleted")
    private short deleted;
    @TableField(value = "email")
    private String email;
    @TableField(value = "age")
    private Integer age;
    @TableField(value = "describe")
    private String describe;
    @TableField(value = "nickname")
    private String nickName;
    @TableField(value = "status")
    private Short status;
    @TableField(value = "pic_url")
    private String picUrl;

    //信息脱敏
    public void desensitized() {
        this.password = null;
        this.email = DesensitizedUtil.email(this.email);
    }

}
