package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @program: mk-cloud
 * @description: 笔记类
 * @author: lizhiyong
 * @create: 2022-02-1A 14:33
 **/

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_notes")
public class MkNotes {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic(value = "deleted")
    private Long deleted;
    @TableField(value = "userId")
    private Long userId;
    @TableField(value = "classic")
    private Long classic;
}