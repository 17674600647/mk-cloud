package com.glm;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableAspectJAutoProxy
public class MkUserStart {
    public static void main(String[] args) {
        SpringApplication.run(MkUserStart.class, args);
    }

}
