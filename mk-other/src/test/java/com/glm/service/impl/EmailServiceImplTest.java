package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glm.MkOtherStart;
import com.glm.entity.ResponseResult;
import com.glm.entity.pojo.MkStatistic;
import com.glm.mapper.StatisticMapper;
import com.glm.service.EmailService;
import com.glm.service.StatisticService;
import com.glm.utils.RedisUtil;
import org.checkerframework.checker.units.qual.A;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = MkOtherStart.class)
public class EmailServiceImplTest {
    @Autowired
    EmailService emailService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    StatisticMapper statisticMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void sendEmail() {
        ResponseResult responseResult = emailService.sendEmail("1044204713@qq.com");
        System.out.println(responseResult);
    }

    @Test
    public void test2() {
        System.out.println((String) redisUtil.get("mkcloud_1044204713@qq.com"));
        System.out.println(redisTemplate.opsForValue().get("mkcloud_1044204713@qq.com"));
    }

    @Test
    public void test3() {
        //java8 新特性获取日期
        LocalDateTime nowDays = LocalDateTime.now();
        LocalDateTime daysBefore = nowDays.minus(30, ChronoUnit.DAYS);
        QueryWrapper<MkStatistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("create_time", daysBefore, nowDays);
        queryWrapper.orderByAsc("create_time");
        List<MkStatistic> mkStatistics = statisticMapper.selectList(queryWrapper);
        System.out.println(mkStatistics);
    }

    @Autowired
    StatisticService statisticService;

    @Test
    public void test4() {
        ResponseResult responseResult = statisticService.queryDataReport();
        System.out.println(responseResult);
    }
}