package com.newcoder.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;
import java.util.List;

/**
 * @author yt
 * date 2022-06-29
 */
@SpringBootTest
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper   discussPostMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","liubei");
        user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectPosts(){
        //用手写的来实现分页
     //   List<DiscussPost> discussPosts = discussPostMapper.selectDiscusPosts(0, 0, 10);
        //使用分页插件
        Page<DiscussPost> discussPostPage = discussPostMapper.selectDiscussPostsPage(new Page<>(1,10),149);

        List<DiscussPost> discussPosts = discussPostPage.getRecords();
        discussPosts.forEach(System.out::println);

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

}
