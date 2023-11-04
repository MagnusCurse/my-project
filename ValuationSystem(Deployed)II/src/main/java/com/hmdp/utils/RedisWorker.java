package com.hmdp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class RedisWorker {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static final long BEGIN_TIMESTAMP = 1695081600; // 开始时间戳

    private static final long COUNT_BIT = 32; // 序列号的位数

    // NOTE 生成一个 64 位的 ID 数,一个符号位, 31 位时间戳, 32 位子增长 ID
    // 不同的业务有不同的 keyPrefix
    public long generateId(String keyPrefix) {
        // TODO 生成时间戳
        long newSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        long timeStamp = newSecond - BEGIN_TIMESTAMP;
        // TODO 生成序列号
        // 获取当前日期, 精确到天, 为了防止 Redis 子增长导致数量过大, 所以按天划分
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 自增长
        long count = stringRedisTemplate.opsForValue().increment("incr:" + keyPrefix + ":" + date);
        // TODO 拼接并且返回
        return timeStamp << COUNT_BIT | count;
    }
}
