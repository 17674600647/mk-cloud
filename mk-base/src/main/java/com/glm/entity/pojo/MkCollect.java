package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
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


    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Field(type= FieldType.Date,format = DateFormat.basic_date_time)
    private Date createTime;

    @TableLogic
    private Short deleted;

}
