package com.glm.utils;


import cn.hutool.json.JSONUtil;
import com.glm.entity.constant.StringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;



@Component
public class MkKafkaUtil {
    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public void send(Object Data) {
        kafkaTemplate.send(StringConstant.KAFKA_TOPIC, JSONUtil.toJsonStr(Data));
    }
}
