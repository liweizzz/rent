package com.liwei.rent.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate redisTemplate;

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    public void set(String key,Object value,long timeout){
        redisTemplate.opsForValue().set(key,value,timeout,TimeUnit.MINUTES);
    }

    public boolean existKey(String key){
        return redisTemplate.hasKey(key);
    }

    public Long increment(String key,long v){
        return redisTemplate.opsForValue().increment(key, v);
    }

    public void setnx(String key,Object value){
        redisTemplate.opsForValue().setIfAbsent(key,value);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }
}
