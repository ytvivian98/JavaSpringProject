package com.newcoder.community.controller;

import com.newcoder.community.entity.Message;
import com.newcoder.community.entity.MyPage;
import com.newcoder.community.entity.User;
import com.newcoder.community.service.MessageService;
import com.newcoder.community.service.UserService;
import com.newcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yt
 * date 2022-07-12
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    //私信列表
    @RequestMapping(path = "/letter/list",method = RequestMethod.GET)
    public  String getLetterList(Model model, MyPage myPage){
        User user = hostHolder.getUser();
        //设置分页信息
        myPage.setLimit(5);
        myPage.setPath("/letter/list");
        myPage.setRows(messageService.findConversationCount(user.getId()));

        //会话列表
        List<Message> conversationList = messageService.findConversations(user.getId(), myPage.getCurrent(), myPage.getLimit());
        List<Map<String , Object>> conversations = new ArrayList<>();
        if(conversationList!=null){
            for(Message message:conversationList){
                Map<String ,Object> map = new HashMap<>();
                map.put("conversation",message);
                map.put("letterCount",messageService.findLettersCount(message.getConversationId()));
                map.put("unreadCount",messageService.findletterUnreadCount(user.getId(),message.getConversationId()));
                int targetId = user.getId() == message.getFromId()?message.getToId():message.getFromId();
                map.put("target",userService.findUserById(targetId));

                conversations.add(map);
            }
        }
        model.addAttribute("conversations",conversations);
        //查询未读消息数量
        int letterUnreadCount = messageService.findletterUnreadCount(user.getId(),null);
        model.addAttribute("letterUnreadCount",letterUnreadCount);

        return "/site/letter";
    }

    @RequestMapping(path = "/letter/detail/{conversationId}",method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, MyPage myPage,Model model){
        //分页信息设置
        myPage.setLimit(5);
        myPage.setPath("/letter/detail/"+conversationId);
        myPage.setRows(messageService.findLettersCount(conversationId));

        List<Message> letterList = messageService.findLetters(conversationId, myPage.getCurrent(), myPage.getLimit());
        List<Map<String,Object>> letters = new ArrayList<>();
        if(letterList!=null){
            for(Message message:letterList){
                Map<String , Object> map = new HashMap<>();
                map.put("letter",message);
                map.put("fromUser",userService.findUserById(message.getFromId()));
                letters.add(map);
            }
        }
        model.addAttribute("letters",letters);

        //私信的目标
        model.addAttribute("target",getLetterTarget(conversationId));
        return "/site/letter-detail";
    }

    private User getLetterTarget(String conversationId){
        String[] ids = conversationId.split("_");
        int d0 = Integer.parseInt(ids[0]);
        int d1 = Integer.parseInt(ids[1]);
        if(hostHolder.getUser().getId() == d0 ){
            return userService.findUserById(d1);
        }
        return userService.findUserById(d0);
    }


}
