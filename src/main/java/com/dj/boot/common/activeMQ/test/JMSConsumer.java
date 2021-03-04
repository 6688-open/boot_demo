package com.dj.boot.common.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JMSConsumer {
    //public static final String ACTIVEMQ_URL = "tcp://192.168.111.11:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue01";
    public static final String TOPIC_NAME = "topic";

    public static void main(String[] args) throws JMSException, IOException {

        //创建连接 按照给定的url  默认的用户名 密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建回话session   1事务 2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); //ACK 签收  倾向于消费者
        //创建目的地  具体是队列 还是topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息生产者
        MessageConsumer messageConsumer = session.createConsumer(queue);//topic
        //同步阻塞
//       while (true) {
//           TextMessage textMessage = (TextMessage) messageConsumer.receive();
//           if (null != textMessage) {
//               System.out.println("消费者受到消息" + textMessage.getText());
//           } else {
//               break;
//           }
//       }
        //监听方式  异步非阻塞
        messageConsumer.setMessageListener((message -> {
            if (null != message &&  message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费者受到消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }));
//        session.commit(); 提交
//        session.recover(); 重试
//        session.rollback(); 回滚
        //关闭资源
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        System.out.println("发送MQ成功");
    }
}
