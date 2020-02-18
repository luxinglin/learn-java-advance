package cn.com.gary.mq.rabbit;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class RabbitMqMessageConsumer extends DefaultConsumer {
    public RabbitMqMessageConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String msg = new String(body, "utf-8");
        log.info("消费端tag: {}, message: {}", consumerTag, msg);
    }
}
