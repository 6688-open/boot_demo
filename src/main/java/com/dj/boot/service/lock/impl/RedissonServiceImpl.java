package com.dj.boot.service.lock.impl;

import com.dj.boot.service.lock.RedissonService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @ProjectName: boot_demo
 * @PackageName: com.dj.boot.service.lock.impl
 * @User: ext.wangjia
 * @Author: wangJia
 * @Date: 2020-08-17-10-47
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    private RedissonClient redissonClient;

    private RLock getLock(String name) {
        RLock lock = redissonClient.getLock(name);
        return lock;
    }

    /**
     * 无指定时间 加锁
     */
    @Override
    public void lock(String name) {
        getLock(name).lock();
    }

    /**
     * 指定时间 加锁
     *
     * @param leaseTime
     * @param unit
     */
    @Override
    public void lock(String name, long leaseTime, TimeUnit unit) {
        getLock(name).lock(30, TimeUnit.SECONDS);
    }

    @Override
    public boolean tryLock(String name) {
        return false;
    }

    @Override
    public boolean tryLock(String name, long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public boolean isLocked(String name) {
        return false;
    }

    @Override
    public void unlock(String name) {
        getLock(name).unlock();
    }
}
