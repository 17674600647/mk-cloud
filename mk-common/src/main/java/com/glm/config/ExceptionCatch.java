package com.glm.config;

import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 异常处理类
 */
@ControllerAdvice//控制器增强
@Slf4j
public class ExceptionCatch {

    //捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception) {
        exception.printStackTrace();
        //记录日志
        log.error("catch exception:{}", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }
}