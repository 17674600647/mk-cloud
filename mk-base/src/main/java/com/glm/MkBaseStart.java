package com.glm;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableDiscoveryClient
@EnableAutoDataSourceProxy
@SpringBootApplication(scanBasePackages = {"com.glm"},exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableAspectJAutoProxy
public class MkBaseStart {
    public static void main(String[] args) {
        SpringApplication.run(MkBaseStart.class, args);
    }
}
