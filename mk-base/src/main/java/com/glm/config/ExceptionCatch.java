package com.glm.config;


import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 异常处理类
 */
@RestControllerAdvice(basePackages="com.glm.controller")//控制器增强
@Log4j2
public class ExceptionCatch {

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("全局异常捕捉器 catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }
    //捕获Exception此类异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult exceptionX(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("全局异常捕捉器 catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error("参数有问题~");
    }

}