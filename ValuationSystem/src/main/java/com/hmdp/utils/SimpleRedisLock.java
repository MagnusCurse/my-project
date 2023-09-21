package com.hmdp.utils;

import cn.hutool.core.util.BooleanUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 自己模拟实现 Redis 锁, 有一些缺陷, 所以后面用 Redisson 代替
 */
public class SimpleRedisLock implements ILock{
    StringRedisTemplate stringRedisTemplate;
    private String name;
    private static final String KEY_PREFIX = "lock:";
    private static final String ID_PREFIX = UUID.randomUUID() + "-";
    // NOTE 创建并初始化 lua 脚本
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;
    // 用静态代码块来初始化 DefaultRedisScript<Long>
    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setLocation(new ClassPathResource("unlock.lua"));
        UNLOCK_SCRIPT.setResultType(Long.class);
    }
    public SimpleRedisLock(String name, StringRedisTemplate stringRedisTemplate) {
        this.name = name;
        this.stringRedisTemplate = stringRedisTemplate;
    }
    @Override
    public boolean tryLock(long timeOutSec) {
        // NOTE 使用线程 id 加 UUID 作为线程标识, 防止锁的误删
        String threadId = ID_PREFIX + Thread.currentThread().getId();
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(KEY_PREFIX + name, threadId, timeOutSec, TimeUnit.SECONDS);
        // NOTE BooleanUtil.isTrue() 防止拆箱导致返回空值问题
        return BooleanUtil.isTrue(success);
    }
    @Override
    public void unLock() {
        // NOTE 调用 lua 脚本,相当于执行下面的一些代码,操作是原子的
        stringRedisTemplate.execute(
                UNLOCK_SCRIPT,
                Collections.singletonList(KEY_PREFIX + name),
                ID_PREFIX + Thread.currentThread().getId()
        );

        // ERR These operation is not atomic,still exist thread safety problems
        // TODO 获取当前线程标识
        // String threadId = ID_PREFIX + Thread.currentThread().getId();
        // TODO 获取锁中的标识
        // String id = stringRedisTemplate.opsForValue().get(KEY_PREFIX + name);
        // TODO 判断线程标识是否一致
        // if(threadId.equals(id)) {
        //    // TODO  如果相等才释放锁
        //    stringRedisTemplate.delete(KEY_PREFIX + name);
        //   }
    }
}
