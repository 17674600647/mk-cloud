package com.glm.entity;

import org.springframework.validation.BindingResult;

public class ResponseResult<T> {
    String code;
    String message;
    T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseResult() {
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult<Object> error(String msg) {
        return new ResponseResult<Object>(ResponseCodeEnum.ERROR.getCode(), msg);
    }

    public static ResponseResult<Object> error(ResponseCodeEnum responseCodeEnum) {
        return new ResponseResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMsg());
    }
    public  ResponseResult<Object> error(ResponseCodeEnum responseCodeEnum,Object data) {
        return new ResponseResult<>(responseCodeEnum.getCode(), responseCodeEnum.getMsg(),data);
    }

    public static ResponseResult<Object> success(String msg) {
        return new ResponseResult<Object>(ResponseCodeEnum.SUCCESS.getCode(), msg);
    }

    public static ResponseResult<Object> success(String msg, Object data) {
        return new ResponseResult<Object>(ResponseCodeEnum.SUCCESS.getCode(), msg, data);
    }


    @Override
    public String toString() {
        return "ResponseResult{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
