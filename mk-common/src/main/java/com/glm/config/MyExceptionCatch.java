package com.glm.config;

import com.glm.config.exception.MessageException;
import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Comparator;
import java.util.stream.Collectors;


/**
 * 异常处理类
 */
@Log4j2
@RestControllerAdvice//控制器增强
public class MyExceptionCatch {
    /**
     * 参数验证统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        assert bindingResult != null;
        String msg = bindingResult.getFieldErrors().stream().sorted(Comparator.comparing(FieldError::getField))
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        log.error("参数验证失败: {},", e.getMessage());
        return ResponseResult.error(msg);
    }

    //捕获MessageException此类异常
    @ExceptionHandler(MessageException.class)
    @ResponseBody
    public ResponseResult exception(MessageException exception) {
        exception.printStackTrace();
        //记录日志
        log.error("全局异常捕捉器 catch exception:", exception.getMessage());
        //返回通用异常
        return ResponseResult.error(exception.getMessage());
    }


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


}