package com.newcoder.community;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.elasticsearch.DiscussPostRepository;
import com.newcoder.community.entity.DiscussPost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * date 2022-07-20
 */

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class elasticsearchTest {


    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

//    @Autowired
//    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void testInsert(){
        //插入一条数据
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));
    }

    @Test
    public void testInsertList(){
        //插入多条数据
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(101,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(102,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(103,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(111,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(112,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(131,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(132,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(133,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscusPosts(134,0,100,0));
    }

    @Test
    public void testUpdate(){
        //修改
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(231);
        discussPost.setContent("我是改了之后的数据");
        discussPostRepository.save(discussPost);
    }

    @Test
    public void testDelete(){
        //删除
        discussPostRepository.deleteById(231);
    }

//    @Test
//    discussPostRepository.search被弃用
//    public void testSearchByRepository(){
//        //搜索
//        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
//                .withSorts(SortBuilders.fieldSort("type").order(SortOrder.DESC))
//                .withSorts(SortBuilders.fieldSort("score").order(SortOrder.DESC))
//                .withSorts(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
//                .withPageable(PageRequest.of(0,10))
//                .withHighlightFields(
//                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
//                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
//                ).build();
//
//        Page<DiscussPost> page =  discussPostRepository.search(searchQuery);
//    }

    @Test
    public void highlightQuery() throws Exception{
        SearchRequest searchRequest = new SearchRequest("discusspost"); //discusspost是索引名，就是表名
        Map<String, Object> res = new HashMap<>();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title")
                .field("content")
                .requireFieldMatch(false)
                .preTags("<em>")
                .postTags("</em>");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder()
                .query(QueryBuilders.multiMatchQuery("互联网寒冬", "title", "content"))
                .sort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .sort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .from(0)// 指定从哪条开始查询
                .size(10)// 需要查出的总记录条数
                .highlighter(highlightBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<DiscussPost> list = new ArrayList<>();
        long total = searchResponse.getHits().getTotalHits().value;

        for(SearchHit hit:searchResponse.getHits().getHits()){
            DiscussPost discussPost = JSONObject.parseObject(hit.getSourceAsString(), DiscussPost.class);

            //处理高亮显示结果
            HighlightField titleField = hit.getHighlightFields().get("title");
            if(titleField!=null){
                discussPost.setTitle(titleField.getFragments()[0].toString());
            }
            HighlightField contentField = hit.getHighlightFields().get("content");
            if(contentField!=null){
                discussPost.setContent(contentField.getFragments()[0].toString());
            }
            System.out.println(discussPost);
            list.add(discussPost);
        }
        res.put("list",list);
        res.put("total",total);
        System.out.println(total);
    }
}
