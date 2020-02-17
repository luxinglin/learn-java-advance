package cn.com.gary.mq.rabbit.comsumer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer4TopicExchange {

    public static void main(String[] args) throws Exception {


        //创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        // 声明
        String exchangeName = "test_topic_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue";
        //String routingKey = "user.*";
        String routingKey = "user.*";
        // 1 声明交换机 
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        // 2 声明队列
        channel.queueDeclare(queueName, false, false, false, null);
        // 3 建立交换机和队列的绑定关系:
        channel.queueBind(queueName, exchangeName, routingKey);

        //durable 是否持久化消息
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, consumer);  
        //循环获取消息  
        while(true){  
            //获取消息，如果没有消息，这一步将会一直阻塞  
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());    
            System.out.println("收到消息：" + msg);  
        } 
    }
}