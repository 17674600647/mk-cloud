package com.glm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum LevelEnum {
    NORMAL(1,"正常操作"),
    WARN(2,"警告"),
    ERROR(3,"严重");
    @Getter
    private Integer level;
    @Getter
    private String  msg;
}
