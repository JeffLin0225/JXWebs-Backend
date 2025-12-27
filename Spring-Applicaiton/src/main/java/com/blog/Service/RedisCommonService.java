package com.blog.Service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisCommonService {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisCommonService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void deleteRedisByKey(String key){
        redisTemplate.delete(key);
    }
}
