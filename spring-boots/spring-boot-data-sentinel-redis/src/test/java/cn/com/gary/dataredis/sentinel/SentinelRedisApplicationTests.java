package cn.com.gary.dataredis.sentinel;

import cn.com.gary.dataredis.cluster.SentinelRedisApplication;
import cn.com.gary.dataredis.cluster.model.User;
import cn.com.gary.dataredis.cluster.service.RedisService;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SentinelRedisApplication.class})// 指定启动类
public class SentinelRedisApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    public void testSentinelSet() {
        User user = new User();
        user.setId("001");
        user.setAge(30);
        user.setName("wangpengfei");

        redisService.sentinelSet(user);
    }

    @Test
    public void testSentinelGet() {
        String str = redisService.sentinelGet("001");
        System.out.println(str);
    }
}