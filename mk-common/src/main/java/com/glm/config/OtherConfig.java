package com.glm.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-12 16:29
 **/

@Configuration
public class OtherConfig {

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/data/upload_file");
        return factory.createMultipartConfig();
    }

}
