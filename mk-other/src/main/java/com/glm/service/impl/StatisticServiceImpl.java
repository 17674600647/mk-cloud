package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glm.entity.ResponseResult;
import com.glm.entity.pojo.MkStatistic;
import com.glm.entity.vo.DataReportArrayVO;
import com.glm.entity.vo.DataReportVo;
import com.glm.mapper.StatisticMapper;
import com.glm.service.StatisticService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@GlobalTransactional
public class StatisticServiceImpl implements StatisticService {
    @Autowired
    StatisticMapper statisticMapper;

    @Override
    public ResponseResult queryDataReport() {
        //java8 新特性获取日期
        LocalDateTime nowDays = LocalDateTime.now();
        LocalDateTime daysBefore = nowDays.minus(30, ChronoUnit.DAYS);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<MkStatistic> queryWrapper = new QueryWrapper<>();
        queryWrapper.between("create_time", daysBefore, nowDays);
        queryWrapper.orderByAsc("create_time");
        List<MkStatistic> mkStatistics = statisticMapper.selectList(queryWrapper);
        DataReportVo dataReportVo = new DataReportVo();
        for (MkStatistic mkStatistic : mkStatistics) {
            String dateF = dateFormatter.format(mkStatistic.getCreateTime());
            int size = dataReportVo.getDays().size();
            if (dataReportVo.getDays().contains(dateF)&&mkStatistic.getLoginNumb()>dataReportVo.getLoginNumbChange().get(size - 1)) {
                dataReportVo.getLoginNumbChange().set(size - 1, mkStatistic.getLoginNumb());
                dataReportVo.getUserNumbChange().set(size - 1, mkStatistic.getUserNumb());
                dataReportVo.getShareNumbChange().set(size - 1, mkStatistic.getShareNumb());
                dataReportVo.getNoteNumbChange().set(size - 1, mkStatistic.getNoteNumb());
            }else if (!dataReportVo.getDays().contains(dateF)){
                dataReportVo.getDays().add(dateF);
                dataReportVo.getLoginNumbChange().add(mkStatistic.getLoginNumb());
                dataReportVo.getUserNumbChange().add(mkStatistic.getUserNumb());
                dataReportVo.getShareNumbChange().add(mkStatistic.getShareNumb());
                dataReportVo.getNoteNumbChange().add(mkStatistic.getNoteNumb());
            }
        }
        return ResponseResult.success("获取成功",new DataReportArrayVO(dataReportVo));
    }
}
