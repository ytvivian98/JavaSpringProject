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
}
