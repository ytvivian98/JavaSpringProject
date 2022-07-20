package com.newcoder.community.event;

import com.alibaba.fastjson.JSONObject;
import com.newcoder.community.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author yt
 * date 2022-07-19
 */
@Component
public class EventProducer {
    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireEvent(Event event){
        //将事件发布到指定的主题,将内容以JSON格式发过去
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
