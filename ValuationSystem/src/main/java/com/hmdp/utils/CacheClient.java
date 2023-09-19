package com.hmdp.utils;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * 缓存工具类封装
 */
@Component
@Slf4j
public class CacheClient {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void set(String key, Object value, Long time, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), time, unit);
    }

    // 尝试获取锁
    public boolean tryLock(String key) {
        // 利用 setnx 指令作为互斥锁, 设置过期时间为 10 秒
        Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(key,"1",10,TimeUnit.SECONDS);
        return BooleanUtil.isTrue(flag); // 使用这个工具类是为了防止拆箱
    }

    // 释放锁
    public void unlock(String key) {
        stringRedisTemplate.delete(key); // 将该 key 删除即可
    }

    /**
     * @param keyPrefix
     * @param id
     * @param type
     * @param dbFallback 操作数据库的函数
     * @param <R> 传入数据的类型
     * @param <ID> 传入 ID 的类型
     */
    public <R, ID> R queryWithPassThrough(String keyPrefix, ID id, Class<R> type,
                                           Function<ID, R> dbFallback, Long time,TimeUnit unit) {
        String key = keyPrefix + id;
        // TODO 从 Redis 中查询数据
        String strJson = stringRedisTemplate.opsForValue().get(key);
        // TODO 判断缓存是否存在
        if(StrUtil.isNotBlank(strJson)) {
            // 存在则直接返回
            return JSONUtil.toBean(strJson,type); // 将 json 转换未 type 类型
        }
        // TODO 缓存不存在, 查询数据库
        R r = dbFallback.apply(id);
        // TODO 数据库不存在, 返回错误
        if(r == null) {
            // 防止缓存穿透,将空值加入 Redis, 并设置过期时间为 2 分钟
            stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
            return null; //
        }
        // TODO 数据库存在, 写入 Redis
        this.set(key,r,time,unit);
        return r;
    }

    /**
     * 实现了设置空值解决缓存穿透
     * 实现了用互斥锁解决缓存击穿
     * @param keyPrefix
     * @param id
     * @param type
     * @param dbFallback 操作数据库的函数
     * @param <R> 传入数据的类型
     * @param <ID> 传入 ID 的类型
     */
    public <R, ID> R queryById(String keyPrefix, ID id, Class<R> type,
                                           Function<ID, R> dbFallback, Long time,TimeUnit unit) {
        String key = keyPrefix + id;
        // TODO 从 Redis 中查询数据
        String strJson = stringRedisTemplate.opsForValue().get(key);
        // TODO 判断缓存是否存在
        if(StrUtil.isNotBlank(strJson)) {
            // 存在则直接返回
            return JSONUtil.toBean(strJson,type); // 将 json 转换未 type 类型
        }

        // TODO 缓存未命中, 尝试获取互斥锁
        String lockKey = RedisConstants.LOCK_SHOP_KEY + id;
        boolean isLock = tryLock(lockKey);
        R r = null; //
        try {
            // 获取锁失败
            if(!isLock) {
                Thread.sleep(50); // 让线程休眠 50 毫秒
                queryById(keyPrefix,id,type,dbFallback,time,unit); // 递归再次调用,再次查询
            }
            // TODO 缓存中不存在, 根据 id 查询数据库 : getById(id) 相当于 select * from tb_shop where id = ?
            r = dbFallback.apply(id);
            if (r == null) {
                // 防止缓存穿透,将空值加入 Redis, 并设置过期时间为 2 分钟
                stringRedisTemplate.opsForValue().set(key, "", RedisConstants.CACHE_NULL_TTL, TimeUnit.MINUTES);
                return null;
            }
            // TODO 存在将其写入 Redis, 转换为 JSON 格式, 并设置过期时间
            this.set(key,r,time,unit);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            unlock(lockKey);
        }
        return r;
    }
}
