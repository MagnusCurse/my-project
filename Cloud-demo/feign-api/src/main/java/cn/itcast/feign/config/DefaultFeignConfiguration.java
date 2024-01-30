package cn.itcast.feign.config;

import cn.itcast.feign.clients.fallback.UserClinetFallbackFactory;
import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level feignLogLevel() {
        // 日志级别设置为 BASIC
        return Logger.Level.BASIC;
    }

    // 将 UserClinetFallbackFactory 注册为一个 Bean
    @Bean
    public UserClinetFallbackFactory userClinetFallbackFactory(){
        return new UserClinetFallbackFactory();
    }
}
