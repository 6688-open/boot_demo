/*
package com.dj.boot.common.activeMQ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;
import java.util.UUID;

@Component
public class Topic_Producer {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Topic topic;


    public void productTopic() {
        jmsMessagingTemplate.convertAndSend(topic, "*****Topic*********"+ UUID.randomUUID().toString().substring(0,6));
    }


    //定时3秒钟投递消息
    @Scheduled(fixedDelay = 3000)
    public void proScheduled(){
        jmsMessagingTemplate.convertAndSend(topic, "*** Topic **Scheduled*********"+ UUID.randomUUID().toString().substring(0,6));
    }
}
*/
