package com.dj.boot.common.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 持久化topic
 */
public class JMSProducer_pub_topic {

    //public static final String ACTIVEMQ_URL = "tcp://192.168.111.11:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue01";
    public static final String TOPIC_NAME = "topic";

    public static void main(String[] args) throws JMSException {

        //创建连接 按照给定的url  默认的用户名 密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //创建回话session   1事务 2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地  具体是队列 还是topic
        Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息生产者
        MessageProducer messageProducer = session.createProducer(topic); //topic
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        //发送消息到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("message" + i);
            //通过messageProducer 发送给mq
            messageProducer.send(textMessage);
        }

        //关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("发送MQ成功");
    }
}
