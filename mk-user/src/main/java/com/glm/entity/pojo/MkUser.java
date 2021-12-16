package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:57
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("mkuser")
public class MkUser {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private java.sql.Date createTime;
    @TableField(value = "update_time",fill=FieldFill.INSERT_UPDATE)
    private java.sql.Date updateTime;
    @TableLogic(value = "deleted")
    private short deleted;
    @TableField(value = "email")
    private String email;
    @TableField(value = "phone")
    private String phone;
    @TableField(value = "nick_name")
    private String nickName;


}
