package com.glm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})  //需要去除数据源的配置
public class MkGatewayStart {
    public static void main(String[] args) {
        SpringApplication.run(MkGatewayStart.class, args);
    }
}
