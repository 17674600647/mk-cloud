package com.glm.utils;

import cn.hutool.db.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.glm.entity.pojo.MkNotes;

import com.glm.mapper.EsMkNotesRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.MoreLikeThisQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

/**
 * @program: mk-cloud
 * @description: ES搜索工具类
 * @author: lizhiyong
 * @create: 2022-03-19 15:20
 **/

@Component
public class EsUtil {
    private static final String MKNOTE_TITLE = "title";
    private static final String MKNOTE_CONTENT = "content";
    private static final String MKNOTE_SHARE_STATUS = "shareStatus";
    private static final String MKNOTE_SHARED = "1";
    private static final String MKNOTE_DELETED = "deleted";
    private static final String MKNOTE_DELETED_FALSE = "0";
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private EsMkNotesRepository esMkNotesRepository;

    /*根据内容/标题搜索分享的文章*/
    public SearchHits<MkNotes> searchNotesOfTitleAndContent(String text, int page, int size) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.matchPhraseQuery(MKNOTE_SHARE_STATUS, MKNOTE_SHARED));
        bqb.must(QueryBuilders.matchPhraseQuery(MKNOTE_DELETED, MKNOTE_DELETED_FALSE));
        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(text, MKNOTE_TITLE, MKNOTE_CONTENT))
                .withFilter(bqb)
                .withPageable(PageRequest.of(page, size))
                .build();
        return elasticsearchRestTemplate.search(queryBuilder, MkNotes.class);
    }
    /*根据标题搜索分享的文章*/
    public SearchHits<MkNotes> searchNotesOfTitle(String text) {
        BoolQueryBuilder bqb = QueryBuilders.boolQuery();
        bqb.must(QueryBuilders.matchPhraseQuery(MKNOTE_SHARE_STATUS, MKNOTE_SHARED));
        bqb.must(QueryBuilders.matchPhraseQuery(MKNOTE_DELETED, MKNOTE_DELETED_FALSE));
        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(text, MKNOTE_TITLE))
                .withFilter(bqb)
                .withHighlightFields(new HighlightBuilder.Field(MKNOTE_TITLE))
                .build();
        return elasticsearchRestTemplate.search(queryBuilder, MkNotes.class);
    }


}
