package cn.com.gary.mq.rabbit.comsumer;

import cn.com.gary.mq.rabbit.RabbitMqMessageConsumer;
import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Consumer4DirectExchange {

    public static void main(String[] args) throws Exception {


        //创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明
        String exchangeName = "test_direct_exchange";
        String exchangeType = "direct";
        String queueName = "test_direct_queue";
        String routingKey = "test.direct";

        //表示声明了一个交换机
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        //表示声明了一个队列
        channel.queueDeclare(queueName, false, false, false, null);
        //建立一个绑定关系:
        channel.queueBind(queueName, exchangeName, routingKey);

        //durable 是否持久化消息
        RabbitMqMessageConsumer defaultConsumer = new RabbitMqMessageConsumer(channel);
        //参数：队列名称、是否自动ACK、Consumer
        channel.basicConsume(queueName, true, defaultConsumer);
    }
}