package com.glm.mapper;

import com.glm.entity.pojo.MkNotes;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @program: mk-cloud
 * @description: Es的笔记搜索
 * @author: lizhiyong
 * @create: 2022-03-19 11:09
 **/


public interface EsMkNotesRepository extends ElasticsearchRepository<MkNotes, Long> {
    public List<MkNotes> findByTitle(String title);

    @Query("{\"match\":{\"id\":{\"query\":?0}}}")
    public MkNotes findByIdValue(Long id);

}
