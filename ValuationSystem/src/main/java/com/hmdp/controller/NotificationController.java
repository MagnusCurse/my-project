package com.hmdp.controller;

import com.hmdp.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 后面再来实现，感觉也不需要这么麻烦
 */
@Slf4j
@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    private static String NOTIFICATION_UN_SHOW = "unshow:";

    /**
     * 关闭改为手机模式通知
     * @param userId
     */
    @GetMapping("/close/{userId}")
    public void closeNotification(@PathVariable("userId") Long userId) {
        String key = NOTIFICATION_UN_SHOW + userId;
        // 在 Redis 中设置不通知显示标志
        stringRedisTemplate.opsForValue().set(key,"");
        // 设置过期时间为 5 分钟
        stringRedisTemplate.expire(key,5,TimeUnit.MINUTES);
    }

    /**
     * 当前通知是否显示
     * @param userId
     * @return
     */
    @GetMapping("/show/{userId}")
    public Result showNotification(@PathVariable("userId") Long userId) {
        String unShowKey = NOTIFICATION_UN_SHOW + userId;
        // 检测当前通知是否为关闭状态
        if(stringRedisTemplate.hasKey(unShowKey) != null) {
            return Result.ok(false);
        }
        return Result.ok(true);
    }
}
