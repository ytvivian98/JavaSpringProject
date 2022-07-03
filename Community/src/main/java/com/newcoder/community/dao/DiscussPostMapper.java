package com.newcoder.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yt
 * date 2022-06-30
 */
@Mapper
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {
    //未使用page插件的分页
    List<DiscussPost> selectDiscusPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit);

    //param注解, 查询数量
    int selectDiscussPostRows(@Param("userId") int userId);

    //使用page插件的分页查看
    Page<DiscussPost> selectDiscussPostsPage(@Param("page") Page<DiscussPost> page, @Param("userId") int userId);
}
