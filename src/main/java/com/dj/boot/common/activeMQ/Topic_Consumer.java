/*
package com.dj.boot.common.activeMQ;


import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Topic_Consumer {


    @JmsListener(destination = "${mytopic}")
    public void receive (TextMessage textMessage) throws JMSException {
        System.out.println("textMessage = " + textMessage.getText());
    }
}
*/
