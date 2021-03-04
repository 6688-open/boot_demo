/*
package com.dj.boot.common.activeMQ;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;
import javax.jms.Topic;

@Component
@EnableJms
public class ActiveConfig {
    @Value("${myqueue}")
    private String myQueue;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }



    @Value("${mytopic}")
    private String myTopic;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(myTopic);
    }



}
*/
