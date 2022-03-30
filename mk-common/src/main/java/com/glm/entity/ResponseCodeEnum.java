package com.glm.entity;

public enum ResponseCodeEnum {
    ERROR("400", "未知的错误"),
    SUCCESS("200", "操作成功~"),
    LOGIN_INVALID("2021X001", "登录时间过长,登录失效"),
    LOGIN_ERROR("2021X002", "登录失败,账号或者密码错误~"),
    PARAMETER_ERROR("2021X003", "参数校验出错"),
    CHECK_LIMIT_ERROR("2021X004", "验证码1分钟发送一次~");


    private final String code;
    private final String msg;

    ResponseCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
