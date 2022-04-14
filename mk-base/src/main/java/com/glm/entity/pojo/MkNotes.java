package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
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
@Document(indexName = "mknotes-ss", shards = 5)
public class MkNotes implements Serializable {
    public static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private Long id;

    @Field(type = FieldType.Text,analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    @TableField(value = "title")
    private String title;


    @TableField(value = "content")
    @Field(type = FieldType.Text,analyzer = "ik_smart", searchAnalyzer = "ik_max_word")
    private String content;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Field(type=FieldType.Date,format = DateFormat.basic_date_time)
    private Date createTime;

    @Field(type=FieldType.Date,format = DateFormat.basic_date_time)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private  Date updateTime;

    @Field(type=FieldType.Short)
    @TableLogic
    private Integer deleted;

    @Field(type=FieldType.Long)
    @TableField(value = "user_id")
    private Long userId;

    @Field(type=FieldType.Text)
    @TableField(value = "classic")
    private Long classic;

    @Field(type=FieldType.Short)
    @TableField(value = "share_status")
    private Short shareStatus;

}