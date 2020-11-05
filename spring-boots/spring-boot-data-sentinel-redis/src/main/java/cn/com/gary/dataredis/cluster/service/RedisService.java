package cn.com.gary.dataredis.cluster.service;

import cn.com.gary.dataredis.cluster.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class RedisService {
    /**
     * 操作字符串的template，StringRedisTemplate是RedisTemplate的一个子集
     */
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * RedisTemplate，可以进行所有的操作
     */
    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public RedisService(StringRedisTemplate stringRedisTemplate, RedisTemplate<Object, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;

        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;

    }

    public void set(String key, String value) {
        ValueOperations<String, String> ops = this.stringRedisTemplate.opsForValue();
        boolean bExistent = this.stringRedisTemplate.hasKey(key);
        if (bExistent) {
            log.warn("this key is Existent, do update!");
            ops.set(key, value);
        } else {
            ops.set(key, value);
        }
    }

    public String get(String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    public void del(String key) {
        this.stringRedisTemplate.delete(key);
    }

    public void sentinelSet(User user) {
        if (user == null) {
            return;
        }

        String key = null;
        try {
            key = new String(user.getId().getBytes("gbk"), StandardCharsets.UTF_8);
        } catch (java.io.UnsupportedEncodingException ex) {
            log.error("UnsupportedEncodingException, ", ex);
        }

        log.info("key is {}", key);
        if (key == null) {
            log.warn("key is null, cannot set to redis");
            return;
        }

        redisTemplate.opsForValue().set(key, user.toString());
    }

    public String sentinelGet(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void flushdb() {
        Object object = redisTemplate.execute(new RedisCallback<Object>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
        log.info("result {}", object);
    }
}
