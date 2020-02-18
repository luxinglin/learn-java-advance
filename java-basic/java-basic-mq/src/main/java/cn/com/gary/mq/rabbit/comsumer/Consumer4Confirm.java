package cn.com.gary.mq.rabbit.comsumer;

import cn.com.gary.mq.rabbit.RabbitMqMessageConsumer;
import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer4Confirm {

    public static void main(String[] args) throws Throwable {
        test2();
    }

    static void test2() throws IOException, TimeoutException, InterruptedException {
        //1 获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //2通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.#";
        String queueName = "test_confirm_queue";

        //3 声明交换机和队列 然后进行绑定设置, 最后制定路由Key
        channel.exchangeDeclare(exchangeName, "topic", true);
        channel.queueDeclare(queueName, true, false, false, null);
        channel.queueBind(queueName, exchangeName, routingKey);

        //4 创建消费者
        RabbitMqMessageConsumer defaultConsumer = new RabbitMqMessageConsumer(channel);
        channel.basicConsume(queueName, true, defaultConsumer);
    }

}