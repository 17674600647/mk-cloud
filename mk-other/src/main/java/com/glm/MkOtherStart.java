package com.glm;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
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
@EnableAutoDataSourceProxy
@SpringBootApplication(scanBasePackages = {"com.glm"},exclude = DataSourceAutoConfiguration.class)
@EnableFeignClients
@EnableAspectJAutoProxy
public class MkOtherStart {
    public static void main(String[] args) {
        SpringApplication.run(MkOtherStart.class, args);
    }
}
