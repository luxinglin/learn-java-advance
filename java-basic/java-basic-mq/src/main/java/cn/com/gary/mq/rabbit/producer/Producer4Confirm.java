package cn.com.gary.mq.rabbit.producer;

import cn.com.gary.mq.rabbit.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * Confirm 确认消息
 * 理解Confirm 消息确认机制：
 * <p>
 * 消息的确认，是指生产者投递消息后，如果Broker收到消息，则会给我们生产者一个应答。
 * 生产者进行接收应答，用来确定这条消息是否正常的发送到Broker，这种方式也是消息的可靠性投递的核心保障！
 * https://www.cnblogs.com/coder-programming/p/11412048.html
 */
public class Producer4Confirm {

    /**
     * 第一步：在channel上开启确认模式：channel.confirmSelect()
     * 第二步；在chanel上 添加监听：addConfirmListener,监听成功和失败的返回结果，根据具体的结果对消息进行重新发送、或记录日志等后续处理！
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //1 创建ConnectionFactory
        Connection connection = ConnectionUtils.getConnection();

        //2 通过Connection创建一个新的Channel
        Channel channel = connection.createChannel();


        //3 指定我们的消息投递模式: 消息的确认模式 
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        //4 发送一条消息
        String msg = "Hello RabbitMQ Send confirm message!";
        channel.basicPublish(exchangeName, routingKey, null, msg.getBytes());

        //5 添加一个确认监听  用于发送消息到Broker端之后，回送消息的监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------no ack!-----------");
            }

            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("-------ack!-----------");
            }
        });
    }
}