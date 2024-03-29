package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_collect")
@Accessors(chain = true)
public class MkCollect {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "note_id")
    private Long noteId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Field(type= FieldType.Date,format = DateFormat.basic_date_time)
    private Date createTime;

    @TableLogic
    private Short deleted;

}
