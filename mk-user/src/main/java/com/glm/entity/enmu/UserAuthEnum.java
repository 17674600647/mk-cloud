package com.glm.entity.enmu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum UserAuthEnum {
    ADMINISTRATOR(2, "管理员"),
    NORMAL_USER(1, "普通用户");
    @Getter
    private Integer mark;
    @Getter
    private String msg;
}
