package cn.itcast.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

public class DefaultFeignConfiguration {
    @Bean
    public Logger.Level feignLogLevel() {
        // 日志级别设置为 BASIC
        return Logger.Level.BASIC;
    }
}
