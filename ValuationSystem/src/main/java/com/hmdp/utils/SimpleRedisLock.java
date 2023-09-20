package com.hmdp.utils;

import cn.hutool.core.util.BooleanUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SimpleRedisLock implements ILock{
    StringRedisTemplate stringRedisTemplate;
    private String name;
    private static final String KEY_PREFIX = "lock:";
    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeOutSec) {
        long threadId = Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId + "");
        // NOTE BooleanUtil.isTrue() 防止拆箱导致返回空值问题
        return BooleanUtil.isTrue(success);
    }

    @Override
    public void unLock() {
        stringRedisTemplate.delete(KEY_PREFIX + name);
    }
}
