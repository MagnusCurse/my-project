package com.hmdp.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Bean
    public RedissonClient redissonClient() {
        // 配置类
        Config config = new Config();
        // 添加 Redis 地址, 这里添加的是单点地址, 也可以使用 config.useClusterServers() 添加集群地址
        config.useSingleServer().setAddress("redis://43.139.61.124:6379").setPassword("137162");
        // 创建客户端
        return Redisson.create(config);
    }
}
