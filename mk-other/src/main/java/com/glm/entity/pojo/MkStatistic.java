package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_statistic")
@Accessors(chain = true)
public class MkStatistic {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    @TableField(value = "user_numb")
    private Integer userNumb;
    @TableField(value = "note_numb")
    private Integer noteNumb;
    @TableField(value = "login_numb")
    private Integer loginNumb;
    @TableField(value = "share_numb")
    private Integer shareNumb;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
