package com.dj.boot.common.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer_sub_topic {
    //public static final String ACTIVEMQ_URL = "tcp://192.168.111.11:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue01";
    public static final String TOPIC_NAME = "topic";

    public static void main(String[] args) throws JMSException, IOException {

        //创建连接 按照给定的url  默认的用户名 密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        //TODO
        connection.setClientID("zs");
        //创建回话session   1事务 2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地  具体是队列 还是topic
        Topic topic = session.createTopic(TOPIC_NAME);

        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "remark..");
        connection.start();


        //同步阻塞
//       while (true) {
        //TextMessage textMessage = (TextMessage) topicSubscriber.receive();
//           if (null != textMessage) {
//               System.out.println("消费者受到消息" + textMessage.getText());
//           } else {
//               break;
//           }
//       }
        //监听方式  异步非阻塞
        topicSubscriber.setMessageListener((message -> {
            if (null != message &&  message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者受到消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }));
        //关闭资源
        System.in.read();
        session.close();
        connection.close();
        System.out.println("发送MQ成功");
    }
}
