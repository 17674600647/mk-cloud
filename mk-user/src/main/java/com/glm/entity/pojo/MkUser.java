package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class MkUser {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private long id;
    @TableField(value = "username")
    private String username;
    @TableField(value = "password")
    private String password;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic(value = "deleted")
    private short deleted;
    @TableField(value = "email")
    private String email;
    @TableField(value = "age")
    private int age;
    @TableField(value = "describe")
    private String describe;
    @TableField(value = "nickname")
    private String nickName;
}
