package com.glm.exception;


import com.glm.entity.ResponseResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 18:19
 */

@Aspect
@Component
public class BindingResultAspect {
    @Around("within(com.glm.controller.*)")
    public Object checkField(ProceedingJoinPoint point) throws Throwable {
        ControllerFieldAspect fieldAspect = point.getTarget().getClass().getAnnotation(ControllerFieldAspect.class);
        if (fieldAspect == null) {
            return point.proceed();
        }
        //TODO 完成对方法注解的解析,获取对应的参数
        Object[] args = point.getArgs();
        Class<?>[] argTypes = new Class[point.getArgs().length];
        BindingResult bindingResult = null;
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
            if (args[i] instanceof BindingResult) {
                bindingResult = (BindingResult) args[i];
            }
        }
        if (bindingResult != null) {
            if (bindingResult.hasErrors()) {
                // log.error(bindingResult.getAllErrors().toString());
                StringBuilder errorStr = new StringBuilder();
                for (ObjectError error : bindingResult.getAllErrors()) {
                    errorStr.append(error.getDefaultMessage());
                }
                return ResponseResult.error("存在以下错误:"+errorStr);
            }
        }
        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return proceed;
    }
}