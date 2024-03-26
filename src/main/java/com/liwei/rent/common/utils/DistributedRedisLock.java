package com.liwei.rent.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DistributedRedisLock {
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 枷锁
     * @param lockName
     * @return
     */
    public boolean lock(String lockName){
        if(redissonClient == null){
            log.info("redis分布式锁redissonClient为空");
            return false;
        }
        RLock lock = redissonClient.getLock(lockName);
        // 锁10秒后自动释放，防止死锁
        lock.lock(10, TimeUnit.SECONDS);
        log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
        return true;
    }

    /**
     * 释放锁
     * @param lockName
     */
    public void unlock(String lockName) {
        if (redissonClient == null) {
            log.info("DistributedRedisLock redissonClient is null");
            return;
        }
        RLock lock = redissonClient.getLock(lockName);
        lock.unlock();
        log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
    }

}
