package com.glm.XxlJob;


import com.glm.entity.pojo.MkStatistic;
import com.glm.mapper.StatisticMapper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class StatisticsUserAndNote {

    @Autowired
    StatisticMapper statisticMapper;

    @XxlJob("MkOtherStatisticJobHandler")
    public void updateData() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>统计定时任务开启~>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Integer queryTotalLogin = statisticMapper.queryTotalLogin();
        Integer queryToTalShare = statisticMapper.queryToTalShare();
        Integer queryTotalNote = statisticMapper.queryTotalNote();
        Integer queryTotalUser = statisticMapper.queryTotalUser();
        MkStatistic mkStatistic = new MkStatistic();
        mkStatistic.setNoteNumb(queryTotalNote)
                .setShareNumb(queryToTalShare)
                .setLoginNumb(queryTotalLogin)
                .setUserNumb(queryTotalUser);
        statisticMapper.insert(mkStatistic);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>统计定时任务结束~>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
