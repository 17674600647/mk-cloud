package com.glm.config.exception;

import com.glm.entity.ResponseCodeEnum;

/**
 * @program: mk-cloud
 * @description: 验证码限制错误
 * @author: lizhiyong
 * @create: 2022-01-24 17:11
 **/


public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BaseException(String message) {
        super(message);
    }
    public BaseException(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMsg());
    }
}
