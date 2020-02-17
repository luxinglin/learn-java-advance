package cn.com.gary.mq.rabbit.producer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Fanout Exchange
 * 不处理路由键，只需要简单的将队里绑定到交换机上
 * <p>
 * 发送到交换机的消息都会被转发到与该交换机绑定的所有队列上
 * <p>
 * Fanout交换机转发消息是最快的
 */
public class Producer4FanoutExchange {


    public static void main(String[] args) throws Exception {

        //1创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();
        //2 创建Channel
        Channel channel = connection.createChannel();
        //3 声明
        String exchangeName = "test_fanout_exchange";
        //4 发送
        for (int i = 0; i < 10; i++) {
            String msg = i + "Coder 编程  Hello World RabbitMQ 4 FANOUT Exchange Message ...";
            channel.basicPublish(exchangeName, "", null, msg.getBytes());
        }
        channel.close();
        connection.close();
    }

}