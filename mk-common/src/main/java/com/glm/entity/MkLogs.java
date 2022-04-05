package com.glm.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.glm.entity.enums.MkLogEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mk_logs")
public class MkLogs {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "user_id")
    private Long userId;
    /**
     * 操作标记
     */
    @TableField(value = "action_mark")
    private Integer actionMark;
    /**
     * 操作描述
     */
    @TableField(value = "action_msg")
    private String actionMsg;
    /**
     * 操作严重程度
     */
    @TableField(value = "level")
    private Integer level;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    public static MkLogs mkLogsByMkLogEnum(MkLogEnum mkLogEnum, Long userId){
        return new MkLogs()
                .setActionMark(mkLogEnum.getActionMark())
                .setActionMsg(mkLogEnum.getActionMsg())
                .setLevel(mkLogEnum.getLevel())
                .setUserId(userId)
                ;
    }
}
