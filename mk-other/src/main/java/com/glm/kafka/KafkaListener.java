package com.glm.kafka;

import cn.hutool.json.JSONUtil;
import com.glm.entity.MkLogs;
import com.glm.mapper.MkLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class KafkaListener {
    @Autowired
    MkLogMapper mkLogMapper;

    @org.springframework.kafka.annotation.KafkaListener(topics = {"mkcloud"})
    public void listen1(String data) {
        MkLogs mkLogs = JSONUtil.toBean(data, MkLogs.class);
        mkLogMapper.insert(mkLogs);
    }
}
