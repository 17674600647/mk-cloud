package com.glm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class ExceptionGlobal extends RuntimeException {
    private static final long serialVersionUID = 1L;
    /**
     * 错误码
     */
    protected String errorCode;
    /**
     * 错误信息
     */
    protected String errorMsg;

    /*错误码*/
    private ResponseCodeEnum responseCodeEnum;

    public ExceptionGlobal() {
        super();
    }

    public ExceptionGlobal(ResponseCodeEnum responseCodeEnum) {
        super(responseCodeEnum.getMsg());
        this.responseCodeEnum = responseCodeEnum;
    }

    public ExceptionGlobal(String errCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
