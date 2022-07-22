package com.newcoder.community.controller;

import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.MyPage;
import com.newcoder.community.entity.SearchResult;
import com.newcoder.community.service.ElasticsearchService;
import com.newcoder.community.service.LikeService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.CommunityConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * date 2022-07-21
 */
@Controller
public class SearchController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    //search?keyword=xxx
    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public String search(String keyword, MyPage page, Model model) {
        //搜索帖子
        SearchResult searchResult = null;
        try {
            searchResult = elasticsearchService.searchDiscussPosts(keyword, (page.getCurrent() - 1)*page.getLimit(), page.getLimit());

        List<DiscussPost> list = searchResult.getList();
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(searchResult != null) {
            for (DiscussPost discussPost : list) {
                Map<String, Object> map = new HashMap<>();
                //帖子
                map.put("post", discussPost);
                //作者
                map.put("user", userService.findUserById(discussPost.getUserId()));
                //点赞数量
                map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, discussPost.getId()));
                discussPosts.add(map);

            }
        }
            model.addAttribute("discussPosts",discussPosts);
            model.addAttribute("keyword",keyword);

            //分页信息
            page.setPath("/search?keyword="+keyword);
            page.setRows(searchResult==null?0:(int) searchResult.getTotal());
        } catch (IOException e) {
            logger.error("搜索数据出错："+e.getMessage());
        }
        return "/site/search";
    }

}
