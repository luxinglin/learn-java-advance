package cn.com.gary.mq.rabbit.producer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * Direct Exchange(直连)
 * 所有发送到Direct Exchange的消息被转发到RouteKey中指定的Queue
 * <p>
 * 注意：Direct模式可以使用RabbitMQ自带的Exchange：default Exchange,所以不需要将Exchange进行任何绑定(binding)操作，消息传递时，RouteKey必须完全匹配才会被队列接收，否则该消息会被抛弃。
 */
public class Producer4DirectExchange {


    public static void main(String[] args) throws Exception {

        //1创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();
        //2创建Channel
        Channel channel = connection.createChannel();
        //3 声明
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";
        //4 发送
        String msg = "Coder编程 Hello World RabbitMQ 4  Direct Exchange Message ... ";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());
    }
}