package com.dhf.config.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 13:29
 */
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     * @param timeOut 过期时间
     */
    public void set(String key, String value, Long timeOut) {
        redisTemplate.opsForValue().set(key, value, timeOut, TimeUnit.SECONDS);
    }

    /**
     * 获取缓存
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public void del(String key) {
        redisTemplate.delete(key);
    }

}
