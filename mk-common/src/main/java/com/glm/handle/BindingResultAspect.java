package com.glm.handle;


import com.glm.entity.ResponseCodeEnum;
import com.glm.entity.ResponseResult;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

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
                return ResponseResult.error(ResponseCodeEnum.PARAMETER_ERROR);
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