package com.example.demo.service;

import com.example.demo.common.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MailRedisService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 将验证码保存到 Redis 中
     * @param code
     */
    public void saveCode(String code) {
        String key = RedisKeyUtils.MAP_KEY_VERIFICATION_CODE;
        redisTemplate.opsForValue().set(key,code);
        // 给该 key 设置为 3 分钟后过期
        redisTemplate.expire(key,180, TimeUnit.SECONDS);
    }

    /**
     * 将待验证邮箱存储到 Redis 中
     * @param mail
     */
    public void saveMail(String mail) {
        String key = RedisKeyUtils.MAP_KEY_VERIFICATION_MAIL;
        redisTemplate.opsForValue().set(key,mail);
        // 给该 key 设置为 3 分钟后过期
        redisTemplate.expire(key,180, TimeUnit.SECONDS);
    }

    /**
     * 从 Redis 中获取验证码
     */
    public String getCode() {
        String key = RedisKeyUtils.MAP_KEY_VERIFICATION_CODE;
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 从 Redis 中获取待验证邮箱
     */
    public String getMail() {
        String key = RedisKeyUtils.MAP_KEY_VERIFICATION_MAIL;
        return redisTemplate.opsForValue().get(key);
    }
}
