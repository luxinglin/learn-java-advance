package cn.com.gary.biz.token.service;

import cn.com.gary.biz.dto.TokenUserDTO;
import cn.com.gary.biz.token.util.GsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Token缓存
 *
 * @author luxinglin
 * @since 2019-09-18
 */
@Component
public class TokenJedisCache {

    private RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

    public void addToken(String token, TokenUserDTO tokenUser, int tokeRedisTimeout) {
        redisTemplate.opsForValue().set(token, GsonUtil.gsonString(tokenUser), tokeRedisTimeout, TimeUnit.MINUTES);
    }

    public void expireToken(String token, int tokeRedisTimeout) {
        redisTemplate.expire(token, tokeRedisTimeout, TimeUnit.MINUTES);
    }

    public TokenUserDTO getToken(String token) {
        if (redisTemplate.hasKey(token)) {
            return GsonUtil.gsonToBean(String.valueOf(redisTemplate.opsForValue().get(token)), TokenUserDTO.class);
        }
        return null;
    }

    public void deleteToken(String token) {
        redisTemplate.delete(token);
    }
}

