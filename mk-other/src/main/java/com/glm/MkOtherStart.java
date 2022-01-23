package com.glm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @program: mk-cloud
 * @description: 启动类
 * @author: lizhiyong
 * @create: 2022-01-23 20:43
 **/

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
public class MkOtherStart {
    public static void main(String[] args) {
        SpringApplication.run(MkOtherStart.class, args);
    }
}
