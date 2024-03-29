package com.newcoder.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.dao.DiscussPostMapper;
import com.newcoder.community.dao.LoginTicketMapper;
import com.newcoder.community.dao.MessageMapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.DiscussPost;
import com.newcoder.community.entity.LoginTicket;
import com.newcoder.community.entity.Message;
import com.newcoder.community.entity.User;
import com.newcoder.community.util.CommunityUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.sql.Wrapper;
import java.util.Date;
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

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private MessageMapper messageMapper;

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
        Page<DiscussPost> discussPostPage = discussPostMapper.selectDiscussPostsPage(new Page<>(1,10),149,0);

        List<DiscussPost> discussPosts = discussPostPage.getRecords();
        discussPosts.forEach(System.out::println);

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abd");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000*60*10));

        loginTicketMapper.insertLoginTicket(loginTicket);
    }
    @Test
    public void testSelectAndUpdateLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abd");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abd",1);
        loginTicket = loginTicketMapper.selectByTicket("abd");
        System.out.println(loginTicket);
    }

    @Test
    public void testMD5(){
        System.out.println(CommunityUtil.md5("123456789" + "1ccef"));
        System.out.println(CommunityUtil.md5("123456789" + "1ccef"));
    }

    @Test
    public void insertPost(){
        DiscussPost discussPost = new DiscussPost();
        discussPost.setTitle("test");
        discussPost.setContent("mytest");
        int i = discussPostMapper.insertDiscussPost(discussPost);
        System.out.println(i);
    }

    @Test
    public void selectMessage(){
        Page<Message> messagePage = messageMapper.selectConversation(new Page<>(1, 20), 111);
        List<Message> records = messagePage.getRecords();
        for(Message message:records){
            System.out.println(message);
        }
        int count =messageMapper.selectConversationCount(111);
        System.out.println(count);

        final Page<Message> messagePage1 = messageMapper.selectLetters(new Page<>(0, 20), "111_112");
        final List<Message> letters = messagePage1.getRecords();
        for(Message message:letters){
            System.out.println(message);
        }

        count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);

        final int count1 = messageMapper.selectLetterUnreadCount(131, "111_131");
        System.out.println(count1);
    }
}
