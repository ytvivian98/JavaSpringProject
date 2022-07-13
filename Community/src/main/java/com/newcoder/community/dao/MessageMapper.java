package com.newcoder.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yt
 * date 2022-07-12
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    //查询当前用户的会话列表，针对每个会话，只返回一条最新的私信
    Page<Message> selectConversation(@Param("page")Page<Message>page, @Param("userId") int userId);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

    //查询某个会话包含的私信列表
    Page<Message> selectLetters(@Param("page")Page<Message> page,String conversationId);

    //查询某个会话包含的私信数量
    int selectLetterCount(String conversationId);

    //查询未读的私信数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //新增一个消息
    int insertMessage(Message message);

    //修改消息的状态
    int updateStatus(List<Integer> ids, int status);
}
