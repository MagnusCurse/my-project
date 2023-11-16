package com.hmdp.config;

import com.hmdp.utils.LoginInterceptor;
import com.hmdp.utils.RefreshTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求, 哪个拦截器先加入哪个先执行
        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate))
                        .addPathPatterns("/**");

        registry.addInterceptor(new LoginInterceptor()).
                excludePathPatterns(
                        "/shop-type/**",
                        "/shop/**",
                        "/shop-comments/show-shop-comments/**",
                        "/voucher/**",
                        "/upload/**",
                        "/blog/hot",
                        "/user/code",
                        "/user/phone-login",
                        "/user/logout");
    }
}
