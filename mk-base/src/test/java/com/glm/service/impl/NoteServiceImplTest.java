package com.glm.service.impl;


import com.glm.MkBaseStart;
import com.glm.entity.pojo.MkNotes;
import org.apache.http.HttpHost;

import org.apache.lucene.index.IndexReader;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NoteServiceImplTest {

    private RestHighLevelClient restClient = null;
    private static final String SCHEME = "http";
    private static final String HOST_NAME = "124.223.65.85";
    private static final HttpHost[] HTTP_HOSTS = {
            new HttpHost(HOST_NAME, 9200, SCHEME),
            new HttpHost(HOST_NAME, 9201, SCHEME),
            new HttpHost(HOST_NAME, 9202, SCHEME),
    };

    @Before
    public void connection() {
        restClient = new RestHighLevelClient(RestClient.builder(HTTP_HOSTS));
    }

    @After
    public void closed() {
        if (restClient != null) {
            try {
                restClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testAdd() {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", "18");
        map.put("addr", "beijing");
        IndexRequest request = new IndexRequest().index("test").id("5").source(map);
        try {
            IndexResponse index = restClient.index(request, RequestOptions.DEFAULT);
            System.out.println(index);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGet() throws IOException {
        GetRequest getRequest = new GetRequest().index("test").id("5");
        GetResponse documentFields = restClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(documentFields);
    }

    @Test
    public void testUpdate() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lizhiyong");
        map.put("age", "48");
        map.put("addr", "shanghai");
        UpdateRequest request = new UpdateRequest().index("test").id("5").doc(map);

        System.out.println(restClient.update(request, RequestOptions.DEFAULT));
    }

    @Test
    public void testDelete() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lizhiyong");
        map.put("age", "48");
        map.put("addr", "shanghai");
        DeleteRequest request = new DeleteRequest().index("test").id("5");
        System.out.println(restClient.delete(request, RequestOptions.DEFAULT));
    }


    //批量操作
    @Test
    public void testBatchCURD() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest().index("index").id("5")
                .source(XContentType.JSON, "name", "lizhiyong10", "age", 18));
        bulkRequest.add(new IndexRequest().index("index").id("6")
                .source(XContentType.JSON, "name", "lizhiyong11", "age", 28));
        bulkRequest.add(new UpdateRequest().index("index").id("5")
                .doc(XContentType.JSON, "name", "lizhiyong1的撒大0", "age", 12338));

        BulkResponse bulk = restClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulk);
    }

    //查询所有
    @Test
    public void testBatchSearch() throws IOException {
        //指定索引库
        SearchRequest searchRequest = new SearchRequest().indices("index", "ik");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //查询所有
        builder.query(QueryBuilders.matchAllQuery());
        searchRequest.source(builder);
        System.out.println(restClient.search(searchRequest, RequestOptions.DEFAULT));
    }

    //根据关键词查询
    @Test
    public void testBatchSearch2() throws IOException {
        //指定索引库
        SearchRequest searchRequest = new SearchRequest().indices("index", "ik");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String key = "li";
        //查询所有
        builder.query(QueryBuilders.multiMatchQuery(key,"name"));
        searchRequest.source(builder);
        System.out.println(restClient.search(searchRequest, RequestOptions.DEFAULT));
    }

    //根据关键词查询，分页查询
    @Test
    public void testBatchSearchPage() throws IOException {
        //指定索引库
        SearchRequest searchRequest = new SearchRequest().indices("index", "ik");
        SearchSourceBuilder builder = new SearchSourceBuilder();
        String key = "li";
        //查询所有
        builder.query(QueryBuilders.multiMatchQuery(key,"name"));
        builder.from(0).size(1);
        searchRequest.source(builder);
        System.out.println(restClient.search(searchRequest, RequestOptions.DEFAULT));
    }





}
