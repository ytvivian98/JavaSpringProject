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
    List<DiscussPost> selectDiscusPosts(@Param("userId") int userId, @Param("offset") int offset, @Param("limit") int limit, int orderMode);

    //param注解, 查询数量
    int selectDiscussPostRows(@Param("userId") int userId);

    //使用page插件的分页查看
    Page<DiscussPost> selectDiscussPostsPage(@Param("page") Page<DiscussPost> page, @Param("userId") int userId, int orderMode);

    //插入
    int insertDiscussPost(DiscussPost discussPost);

    //查询帖子的详情
    DiscussPost selectDiscussPostById(int id);

    //更新评论数量
    int updateCommentCountInt(int id, int commentCount);

    //修改帖子的类型
    int updateType(int id, int type);

    //修改帖子的状态
    int updateStatus(int id, int Status);

    //修改帖子的分数
    int updateScore(int id, double score);

}
