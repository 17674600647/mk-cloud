package com.glm.entity.enmu;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public enum TokenPrefixEnum {
    TokenPre("token_", "token前缀标志"),
    TOKEN_USER_INFO("token_user_info_", "token前缀标志"),
    TOKEN_USER_INFO_OUT("token_user_info_out", "token前缀标志");

    @Getter
    private String prefix;
    @Getter
    private String msg;

}
