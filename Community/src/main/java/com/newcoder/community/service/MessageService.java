package com.newcoder.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newcoder.community.dao.MessageMapper;
import com.newcoder.community.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yt
 * date 2022-07-12
 */
@Service
public class MessageService {

    @Autowired
    private MessageMapper messageMapper;

    public List<Message> findConversations(int userId, int cur, int limit){
        Page<Message> messagePage = messageMapper.selectConversation(new Page<>(cur, limit), userId);
        return messagePage.getRecords();
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int cur, int limit){
        Page<Message> messagePage = messageMapper.selectLetters(new Page<>(cur, limit), conversationId);
        return messagePage.getRecords();
    }

    public int findLettersCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findletterUnreadCount(int userId, String conversationId){
        return messageMapper.selectLetterUnreadCount(userId,conversationId);
    }
}
