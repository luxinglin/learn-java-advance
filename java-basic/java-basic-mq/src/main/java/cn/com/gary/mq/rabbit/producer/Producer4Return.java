package cn.com.gary.mq.rabbit.producer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * Return消息机制
 * Return Listener用于处理一些不可路由的消息！
 * 我们的消息生产者，通过指定一个Exchange和Routingkey，把消息送达到某一个队列中去，然后我们的消费者监听队列，进行消费处理操作！
 * 但是在某些情况下，如果我们在发送消息的时候，当前的exchange不存在或者指定的路由key路由不到，这个时候如果我们需要监听这种不可达的消息，就要使用Return Listener!
 * 在基础API中有一个关键的配置项：
 * <p>
 * Mandatory:如果为true，则监听器会接收到路由不可达的消息，然后进行后续处理，如果为false,那么broker端自动删除该消息！
 * https://www.cnblogs.com/coder-programming/p/11412048.html
 */
public class Producer4Return {


    public static void main(String[] args) throws Exception {
        //1 创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        String exchange = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";

        String msg = "Hello RabbitMQ Return Message";


        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange,
                                     String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.err.println("---------handle  return----------");
                //响应码
                System.err.println("replyCode: " + replyCode);
                //响应文本
                System.err.println("replyText: " + replyText);
                System.err.println("exchange: " + exchange);
                System.err.println("routingKey: " + routingKey);
                System.err.println("properties: " + properties);
                System.err.println("body: " + new String(body));
            }
        });

        //第三个参数mandatory=true,意味着路由不到的话mq也不会删除消息,false则会自动删除
        channel.basicPublish(exchange, routingKey, true, null, msg.getBytes());
        //修改routingkey，测试是否能够收到消息
        channel.basicPublish(exchange, routingKeyError, true, null, msg.getBytes());

    }
}