package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.glm.entity.ResponseResult;
import com.glm.entity.constant.StringConstant;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.vo.CollectNoteVO;
import com.glm.entity.vo.LeaderboardVO;
import com.glm.entity.vo.ObjectPageVO;
import com.glm.mapper.MkNoteMapper;
import com.glm.service.MkCollectService;

import com.glm.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class MkCollectServiceImpl implements MkCollectService {
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    MkNoteMapper mkNoteMapper;

    @Override
    public ResponseResult getLeaderboard() {
        Map<String, Double> stringDoubleMap = redisUtil.ZSetGetList(StringConstant.LEADERBOARD_COLLECT, 0, -1);
        if (stringDoubleMap.size() == 0) {
            return ResponseResult.success("查询成功,暂无数据~");
        }
        List<CollectNoteVO> collectNoteVo = CollectNoteVO.getCollectNoteVo(stringDoubleMap);
        List<Long> collectNoteIds = collectNoteVo.stream().map(x -> Long.valueOf(x.getKey())).collect(Collectors.toList());
        QueryWrapper<MkNotes> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "title");
        queryWrapper.in("id", collectNoteIds);
        List<MkNotes> mkNotes = mkNoteMapper.selectList(queryWrapper);
        List<LeaderboardVO> leaderboardVOS = LeaderboardVO.voFromkNotes(mkNotes, stringDoubleMap);
        return ResponseResult.success("查询成功",leaderboardVOS);
    }
}
