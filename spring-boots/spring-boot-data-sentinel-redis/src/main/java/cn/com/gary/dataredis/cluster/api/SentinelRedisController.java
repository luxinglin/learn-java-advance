package cn.com.gary.dataredis.cluster.api;

import cn.com.gary.dataredis.cluster.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by Janus on 2019/1/11.
 */
@Controller
public class SentinelRedisController {

    @Autowired
    RedisService redisService;

    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        System.out.println("进入test方法 "+ LocalDateTime.now());
        int value= (int)(Math.random()*100);
        String v2="v"+value;
        redisService.set("name",v2);
        String name = redisService.get("name");
        return name;
    }
}
