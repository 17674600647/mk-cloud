package com.glm.service.impl;


import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@SpringBootTest(classes = Testxx.class)
public class Testxx {
    @Test
    public void test() {
        //java8 新特性
        LocalDateTime now = LocalDateTime.now();
        now = now.minus(30, ChronoUnit.DAYS);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = dtf2.format(now);
        System.out.println(format);
    }
}
