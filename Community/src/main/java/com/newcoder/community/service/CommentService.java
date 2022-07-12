package com.newcoder.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.dao.CommentMapper;
import com.newcoder.community.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yt
 * date 2022-07-11
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findCommentsByEntity(int entityType, int entityId,int offset, int limit){
        Page<Comment> comments = commentMapper.selectCommentsByEntity(new Page<>(offset,limit),entityType,entityId);
        return comments.getRecords();
    }

    public int findCommentCount(int entityType, int entityId){
        return commentMapper.selectCountByEntity(entityType,entityId);
    }
}
