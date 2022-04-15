package com.glm.config;

import com.glm.config.exception.TestException;
import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-02-15 18:22
 **/

@Log4j2
@RestControllerAdvice
public class ExceptionPlus extends  MyExceptionCatch{
    //捕获Exception此类异常
    @ExceptionHandler(TestException.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("全局异常捕捉器 catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exceptionX(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("全局异常捕捉器 catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }
}
