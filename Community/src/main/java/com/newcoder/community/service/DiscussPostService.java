package com.newcoder.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yt
 * date 2022-07-03
 */
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPost(int userId, int offset, int limit){
        //使用自定义分页插件的方式查看
        Page<DiscussPost> discussPostPage = discussPostMapper.selectDiscussPostsPage(new Page<>(offset, limit), userId);
        return discussPostPage.getRecords();
        //自定义sql语句，xml
      //  return discussPostMapper.selectDiscusPosts(userId,offset,limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
