package com.glm.config;

import com.glm.entity.pojo.MkNotes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;

import javax.annotation.PostConstruct;

/**
 * @program: mk-cloud
 * @description: Es的索引配置类
 * @author: lizhiyong
 * @create: 2022-03-19 15:22
 **/

@Configuration
@Log4j2
public class EsIndexConfig {
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @PostConstruct
    public void mappingIndex() {
        //获取索引对象
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(MkNotes.class);
        boolean exists = indexOperations.exists();
        if (exists) {
            log.info(MkNotes.class.getName()+"索引已经存在");
            return;
        }
        //创建索引
        indexOperations.create();
        //获取映射
        Document document = indexOperations.createMapping(MkNotes.class);
        //将映射放入索引
        indexOperations.putMapping(document);
        log.info(MkNotes.class.getName()+"索引创建成功~");
    }
}
