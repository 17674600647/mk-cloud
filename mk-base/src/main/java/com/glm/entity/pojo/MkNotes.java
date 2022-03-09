package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonSerialize(using= ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "title")
    private String title;
    @TableField(value = "content")
    private String content;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    private Integer deleted;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "classic")
    private Long classic;
}