package com.glm.config.exception;


import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
    //捕获Exception此类异常
    @ExceptionHandler(value=BaseException.class)
    public ResponseResult baseException(BaseException exception){
        exception.printStackTrace();
        //记录日志
        log.error("(异常捕捉)catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("(异常捕捉)catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }
}
