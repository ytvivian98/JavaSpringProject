package com.newcoder.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.newcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yt
 * date 2022-06-29
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
