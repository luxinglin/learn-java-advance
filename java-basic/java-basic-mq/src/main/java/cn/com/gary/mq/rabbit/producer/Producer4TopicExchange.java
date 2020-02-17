package cn.com.gary.mq.rabbit.producer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Topic Exchange
 * 所有发送到Topic Exchange的消息被转发到所有管线RouteKey中指定Topic的Queue上
 * <p>
 * Exchange将RouteKey和某Topic进行模糊匹配,此时队列需要绑定一个Topic
 */
public class Producer4TopicExchange {


    public static void main(String[] args) throws Exception {

        //1创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();
        //2创建Channel
        Channel channel = connection.createChannel();
        //3声明
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";
        //4发送
        String msg = "Coder编程 Hello World RabbitMQ 4 Topic Exchange Message ...";
        channel.basicPublish(exchangeName, routingKey1, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey2, null, msg.getBytes());
        channel.basicPublish(exchangeName, routingKey3, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}