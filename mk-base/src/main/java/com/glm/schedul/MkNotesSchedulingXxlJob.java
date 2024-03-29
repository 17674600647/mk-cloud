package com.glm.schedul;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.glm.entity.constant.StringConstant;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.pojo.MkScheduling;
import com.glm.entity.vo.CollectNoteVO;
import com.glm.mapper.EsMkNotesRepository;
import com.glm.mapper.MkCollectMapper;
import com.glm.mapper.MkNoteMapper;
import com.glm.mapper.MkSchedulingMapper;
import com.glm.utils.RedisUtil;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: mk-cloud
 * @description: 笔记同步类
 * @author: lizhiyong
 * @create: 2022-03-20 22:30
 **/

@Component
@Log4j2
public class MkNotesSchedulingXxlJob {
    @Autowired
    private EsMkNotesRepository esMkNotesRepository;

    @Autowired
    MkNoteMapper mkNotesMapper;

    @Autowired
    MkCollectMapper mkCollectMapper;

    @Autowired
    MkSchedulingMapper mkSchedulingMapper;

    @Autowired
    RedisUtil redisUtil;
    public static final Integer MKNOTE_TO_ES = 1;

    /*每10分钟执行一次，使用xxj-job*/

    /**
     * 同步新增的文章
     */
    //@Scheduled(fixedDelay = 600000)
    @XxlJob("MkbaseSyncArticlesJobHandler")
    public void updateDataToES() {
        log.info("-----------开始执行定时更新文章的任务------------------");
        QueryWrapper<MkScheduling> mkSchedulingQueryWrapper = Wrappers.<MkScheduling>query()
                .orderByDesc("create_time")
                .eq("task_number", MKNOTE_TO_ES);
        List<MkScheduling> mkSchedulings = mkSchedulingMapper.selectList(mkSchedulingQueryWrapper);
        Date lastUpdateDate = null;
        Date nowUpdateTime = null;
        if (mkSchedulings.size() == 0) {
            //如果没有任务，就设置为项目创建日期
            lastUpdateDate = new Date(1639584000000L);
            nowUpdateTime = new Date();
        } else {
            for (MkScheduling mkScheduling : mkSchedulings) {
                if (mkScheduling.getCreateTime() != null && mkScheduling.getOverTime() != null) {
                    //为了容错，多检测一秒
                    lastUpdateDate = new Date(mkScheduling.getCreateTime().getTime() - 1000);
                    nowUpdateTime = new Date();
                    break;
                }
            }
        }
        //查找晚于这个更新时间的
        QueryWrapper<MkNotes> queryWrapper = new QueryWrapper<MkNotes>();
        queryWrapper.between("update_time", lastUpdateDate, nowUpdateTime);
        List<MkNotes> mkNotes = mkNotesMapper.selectList(queryWrapper);
        List<Long> longs = mkNotes.stream().map(MkNotes::getId).collect(Collectors.toList());
        esMkNotesRepository.saveAll(mkNotes);
        MkScheduling mkSchedulingX = new MkScheduling();
        mkSchedulingX.setCreateTime(nowUpdateTime);
        mkSchedulingX.setOverTime(new Date());
        mkSchedulingX.setTaskNumber(MKNOTE_TO_ES);
        mkSchedulingX.setUpdateNumber(longs.size());
        mkSchedulingX.setStartTime(lastUpdateDate);
        mkSchedulingMapper.insert(mkSchedulingX);
        log.info("本次更新的文章ID" + longs);
        log.info("-----------定时任务结束------------------");
    }

    @XxlJob("statisticalCollectionOfDataJobHandler")
    public void updateDataToRedis() {
        List<CollectNoteVO> collectNoteVOS = mkCollectMapper.countCollectNumb();
        Map<String, Double> collectMap = collectNoteVOS.stream().collect(Collectors.toMap(CollectNoteVO::getKey, CollectNoteVO::getScore));
        redisUtil.ZSetBatchADD(StringConstant.LEADERBOARD_COLLECT,collectMap);
    }
}
