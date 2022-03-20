package com.glm.schedul;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.pojo.MkScheduling;
import com.glm.mapper.EsMkNotesRepository;
import com.glm.mapper.MkNoteMapper;
import com.glm.mapper.MkSchedulingMapper;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mk-cloud
 * @description: 笔记同步类
 * @author: lizhiyong
 * @create: 2022-03-20 22:30
 **/

@Component
@Log4j2
public class MkNotesScheduling {
    @Autowired
    private EsMkNotesRepository esMkNotesRepository;

    @Autowired
    MkNoteMapper mkNotesMapper;

    @Autowired
    MkSchedulingMapper mkSchedulingMapper;

    public static final Integer MKNOTE_TO_ES = 100;

    /*每10分钟执行一次*/
    @Scheduled(fixedDelay=600000)
    public void updateDataToES() {
        log.info("-----------开始执行定时更新文章的任务------------------");
        QueryWrapper<MkScheduling> mkSchedulingQueryWrapper = Wrappers.<MkScheduling>query()
                .orderByDesc("create_time")
                .eq("task_number", MKNOTE_TO_ES);
        List<MkScheduling> mkSchedulings = mkSchedulingMapper.selectList(mkSchedulingQueryWrapper);
        Date lastUpdateDate = null;
        if (mkSchedulings == null) {
            lastUpdateDate = new Date();
        } else {
            for (MkScheduling mkScheduling : mkSchedulings) {
                if (mkScheduling.getCreateTime() != null && mkScheduling.getOverTime() != null) {
                    lastUpdateDate = mkScheduling.getCreateTime();
                    break;
                }
            }
        }
        Date nowUpdateTime = new Date();
        //查找晚于这个更新时间的
        QueryWrapper<MkNotes> queryWrapper = new QueryWrapper<MkNotes>();
        queryWrapper.between("update_time", lastUpdateDate, nowUpdateTime);
        List<MkNotes> mkNotes = mkNotesMapper.selectList(queryWrapper);
        List<Long> longs = mkNotes.stream().map(MkNotes::getId).collect(Collectors.toList());
        esMkNotesRepository.saveAll(mkNotes);
        MkScheduling mkSchedulingX=new MkScheduling();
        mkSchedulingX.setCreateTime(nowUpdateTime);
        mkSchedulingX.setOverTime(new Date());
        mkSchedulingX.setTaskNumber(MKNOTE_TO_ES);
        mkSchedulingMapper.insert(mkSchedulingX);
        log.info("本次更新的文章ID" + longs);
        log.info("-----------定时任务结束------------------");
    }
}
