package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_scheduling")
public class MkScheduling implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private long id;

    @TableField(value = "task_number")
    private Integer taskNumber;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(value = "over_time")
    private Date overTime;
}