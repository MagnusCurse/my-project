//package com.hmdp.config;
//
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedisConfig {
//    @Bean
//    public RedissonClient redissonClient() {
//        // 配置类
//        Config config = new Config();
//        // 添加 Redis 地址, 这里添加的是单点地址, 也可以使用 config.useClusterServers() 添加集群地址
//        config.useSingleServer().setAddress("redis://43.139.61.124:6379").setPassword("137162");
//        // 创建客户端
//        return Redisson.create(config);
//    }
//}

package com.hmdp.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;   // better use int

    @Value("${spring.redis.password:}") // optional, in case Redis has password
    private String redisPassword;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String redisAddress = String.format("redis://%s:%d", redisHost, redisPort);

        // single node mode
        if (redisPassword != null && !redisPassword.isEmpty()) {
            config.useSingleServer()
                    .setAddress(redisAddress)
                    .setPassword(redisPassword);
        } else {
            config.useSingleServer()
                    .setAddress(redisAddress);
        }

        return Redisson.create(config);
    }
}
