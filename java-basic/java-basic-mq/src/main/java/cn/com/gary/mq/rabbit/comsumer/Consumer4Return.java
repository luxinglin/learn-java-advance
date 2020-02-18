package cn.com.gary.mq.rabbit.comsumer;

import cn.com.gary.mq.rabbit.RabbitMqMessageConsumer;
import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Return {


    public static void main(String[] args) throws Throwable {
        test1();
    }

    static void test1() throws IOException, TimeoutException, InterruptedException {
        //1 创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_return_exchange";
        String routingKey = "return.#";
        String queueName = "test_return_queue";

        channel.exchangeDeclare(exchangeName, "topic", true, false, null);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);


        RabbitMqMessageConsumer queueingConsumer = new RabbitMqMessageConsumer(channel);

        channel.basicConsume(queueName, true, queueingConsumer);

    }

}