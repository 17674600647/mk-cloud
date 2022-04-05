package com.glm.entity.enums;

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

    public static UserAuthEnum getByMark(Integer mark) {
        UserAuthEnum[] userAuthEnum = values();
        for (UserAuthEnum authEnum : userAuthEnum) {
            if (authEnum.mark.equals(mark)) {
                return authEnum;
            }
        }
        return null;
    }
}
