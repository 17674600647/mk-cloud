package com.glm.config;

import com.glm.handle.ControllerFieldAspect;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 18:19
 */
@Configuration
@ComponentScan("com.glm.handle")
public class BindingResultAspectConfig {



}
