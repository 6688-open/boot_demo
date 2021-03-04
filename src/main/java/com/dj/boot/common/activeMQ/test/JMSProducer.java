package com.dj.boot.common.activemq.test;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {

    //public static final String ACTIVEMQ_URL = "tcp://192.168.111.11:61616";
    public static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "queue01";
    public static final String TOPIC_NAME = "topic";

    public static void main(String[] args) throws JMSException {

        //创建连接 按照给定的url  默认的用户名 密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //异步投递
        //activeMQConnectionFactory.setUseAsyncSend(true);
        //获得连接并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        //创建回话session   1事务 2 签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地  具体是队列 还是topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //Topic topic = session.createTopic(TOPIC_NAME);
        //创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue); //topic
        //messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //messageProducer.setDeliveryDelay(2000L);
        //发送消息到MQ队列里面
        for (int i = 0; i < 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("message" + i);
            //延迟投递   定时投递
//            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, 3000);
//            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, 4000);
//            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 3000);
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
