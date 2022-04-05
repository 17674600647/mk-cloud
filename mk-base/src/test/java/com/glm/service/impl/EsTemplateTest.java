package com.glm.service.impl;

import com.glm.entity.pojo.MkNotes;
import com.glm.mapper.EsMkNotesRepository;
import com.glm.mapper.MkNoteMapper;
import com.glm.utils.EsUtil;
import lombok.extern.log4j.Log4j2;
import org.checkerframework.checker.units.qual.A;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-19 10:53
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
public class EsTemplateTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private EsMkNotesRepository esMkNotesRepository;

    @Autowired
    MkNoteMapper mkNotesMapper;

    @Test
    public void testDataEs() {
        //获取索引对象
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(MkNotes.class);
        //创建索引
        indexOperations.create();
        //获取映射
        Document document = indexOperations.createMapping(MkNotes.class);
        //将映射放入索引
        indexOperations.putMapping(document);
        boolean exists = indexOperations.exists();
        System.out.println(exists);
    }

    @Test
    public void testDataEsX() {
        //获取索引对象
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(MkNotes.class);

        boolean exists = indexOperations.exists();
        if (exists) {
            indexOperations.delete();
            log.error("索引被删除~");
        }
        System.out.println(exists);
    }


    //根据标题查询数据
    @Test
    public void testSearch() {
        List<MkNotes> byTitle = esMkNotesRepository.findByTitle("%萨芬%");
        byTitle.forEach(System.out::println);
    }

    //自定义查询语句查询数据
    @Test
    public void testSearch2() {
        MkNotes mkNotes = esMkNotesRepository.findByIdValue(Long.valueOf("1501920646377177089"));
        System.out.println(mkNotes);
    }

    //删除一个信息
    @Test
    public void testDelete() {
        String mknotes = elasticsearchRestTemplate.delete("1500073767171444737", IndexCoordinates.of("mknotes"));
        System.out.println(mknotes);
    }


    //删除一个信息
    @Test
    public void testSearchC() {
        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("title", "萨的芬"))
                .build();
        SearchHits<MkNotes> search = elasticsearchRestTemplate.search(queryBuilder, MkNotes.class);
        search.getSearchHits().forEach(System.out::println);
    }

    //高级查询
    /*分页，高亮，排序*/
    @Test
    public void testSearchHigh() {
        NativeSearchQuery queryBuilder = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("萨的芬", "title"))
                //分页，排序
                .withPageable(PageRequest.of(0, 3))
                //排序分数正序,默认倒序
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                //根据id倒序
                //.withSort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                //高亮,默认 em 斜体
                //.withHighlightFields(new HighlightBuilder.Field("title"))
                //自定义高亮格式
                .withHighlightBuilder(new HighlightBuilder().field("title").preTags("<span style='color:red'>").postTags("</span>"))
                .build();
        SearchHits<MkNotes> search = elasticsearchRestTemplate.search(queryBuilder, MkNotes.class);
        for (SearchHit<MkNotes> hits : search) {
            System.out.println("内容" + hits.getContent());
            System.out.println("ID" + hits.getId());
            List<Object> sortValues = hits.getSortValues();
            sortValues.forEach(System.out::println);
            System.out.println("排序的值" + sortValues);
            System.out.println("分数" + hits.getScore());
            System.out.println(hits.getHighlightFields().get(0));
        }
    }

    @Autowired
    EsUtil esUtil;

    //把数据存到es
    @Test
    public void testSave() {
        List<MkNotes> mkNotes = mkNotesMapper.selectList(null);
        esMkNotesRepository.saveAll(mkNotes);
        System.out.println(mkNotes);
    }

    @Test
    public void test67() {
        SearchHits<MkNotes> search = esUtil.searchNotesOfTitle("我的");
        for (SearchHit<MkNotes> hits : search) {
            System.out.println("-----------开始----------");
            System.out.println("内容" + hits.getContent());
            System.out.println("ID" + hits.getId());
            List<Object> sortValues = hits.getSortValues();
            sortValues.forEach(System.out::println);
            System.out.println("排序的值" + sortValues);
            System.out.println("分数" + hits.getScore());
            System.out.println("-----------结束----------");
        }
        System.out.println("--------总共命中---------");
        System.out.println(search.getTotalHits());
    }

    @Test
    public void test8() {
        SearchHits<MkNotes> search = esUtil.searchNotesOfTitle3("我的");
        for (SearchHit<MkNotes> hits : search) {
            System.out.println("-----------开始----------");
            System.out.println("标题" + hits.getContent().getTitle());
            System.out.println("ID" + hits.getId());
            List<Object> sortValues = hits.getSortValues();
            sortValues.forEach(System.out::println);
            System.out.println("排序的值" + sortValues);
            System.out.println("分数" + hits.getScore());
            System.out.println("-----------结束----------");
        }
        System.out.println("--------总共命中---------");
        System.out.println(search.getTotalHits());
    }

    @Test
    public void test9() {
        System.out.println(new Date());
    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    public void test() {
        String messge = "shabi1";
        kafkaTemplate.send("topic1", "topci1:" + messge);
        kafkaTemplate.send("topic2", "topci2:" + messge);
    }

}
