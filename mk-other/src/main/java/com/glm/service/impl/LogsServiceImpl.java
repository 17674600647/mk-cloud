package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.entity.MkLogs;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.QueryLogsPageDTO;
import com.glm.entity.vo.LogsVO;
import com.glm.mapper.MkLogMapper;
import com.glm.service.LogsService;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.logging.LogManager;

@Service
@GlobalTransactional
public class LogsServiceImpl implements LogsService {
    @Autowired
    MkLogMapper mkLogMapper;

    @Override
    public ResponseResult queryLogsByLevel(QueryLogsPageDTO queryLogsPage) {
        QueryWrapper<MkLogs> queryWrapper = new QueryWrapper<MkLogs>();
        queryWrapper.orderByDesc("create_time");
        IPage<MkLogs> page = new Page<MkLogs>(queryLogsPage.getCurrentPage(), queryLogsPage.getPageSize());
        if (queryLogsPage.getLevel()!=0){
            queryWrapper.eq("level",queryLogsPage.getLevel());
        }
        if (!StringUtils.isBlank(queryLogsPage.getKeyWords())){
            queryWrapper.like("action_msg",queryLogsPage.getKeyWords());
        }
        IPage<MkLogs> mkLogsIPage = mkLogMapper.selectPage(page, queryWrapper);
        LogsVO logsVO = new LogsVO();
        logsVO.setTotal((int) mkLogsIPage.getTotal());
        logsVO.setLogList(mkLogsIPage.getRecords());
        return ResponseResult.success(logsVO);
    }
}
