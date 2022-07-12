package com.newcoder.community.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author yt
 * date 2022-07-11
 */
@Mapper
public interface CommentMapper {

    //使用分页插件
    Page<Comment> selectCommentsByEntity(@Param("page")Page<Comment>page, @Param("entityType") int entityType, @Param("entityId") int entityId);

    //查询一共有多少数据
    int selectCountByEntity(@Param("entityType") int entityType, @Param("entityId") int entityId);

    //增加帖子评论
    int insertComment(Comment comment);
}
