package cn.com.gary.mq.rabbit.comsumer;

import cn.com.gary.mq.rabbit.RabbitMqMessageConsumer;
import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Consumer4FanoutExchange {

    public static void main(String[] args) throws Exception {

        //创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        // 声明
        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String routingKey = ""; //不设置路由键
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        //durable 是否持久化消息
        RabbitMqMessageConsumer defaultConsumer = new RabbitMqMessageConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, defaultConsumer);
    }
}