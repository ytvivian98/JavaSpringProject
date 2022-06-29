package com.newcoder.community;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newcoder.community.dao.UserMapper;
import com.newcoder.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Wrapper;

/**
 * @author yt
 * date 2022-06-29
 */
@SpringBootTest
public class MapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username","liubei");
        user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }
}
