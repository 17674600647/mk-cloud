package com.glm.entity.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("mk_statistic")
public class MkStatistic {
    @TableId(value = "id")
    private Long id;
    @TableField(value = "task_number")
    private Long userNumb;
    @TableField(value = "task_number")
    private Long noteNumb;
    @TableField(value = "loginNumb")
    private Long loginNumb;
    @TableField(value = "shareNumb")
    private Long shareNumb;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
