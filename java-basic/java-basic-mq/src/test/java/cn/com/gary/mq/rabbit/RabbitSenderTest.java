package cn.com.gary.mq.rabbit;

import cn.com.gary.mq.MqApplication;
import cn.com.gary.mq.rabbit.model.Order;
import cn.com.gary.mq.rabbit.producer.RabbitSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitSender Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2�� 17, 2020</pre>
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MqApplication.class})
@Slf4j
public class RabbitSenderTest {

    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Autowired
    RabbitSender rabbitSender;

    @Before
    public void before() throws Exception {
        log.info("before");
    }

    @After
    public void after() throws Exception {
        log.info("after");
    }

    /**
     * Method: send(Object message, Map<String, Object> properties)
     */
    @Test
    public void testSend() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put("number", "12345");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello RabbitMQ For Spring Boot!", properties);
        Thread.sleep(2000);
    }

    @Test
    public void testSenderOrder() throws Exception {
        Order order = Order.builder().
                id(1L).
                name("测试订单1").
                build();
        rabbitSender.sendOrder(order);
        //防止资源提前关闭，ConfirmCallback异步回调失败
        Thread.sleep(2000);
    }
} 
