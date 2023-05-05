package com.example.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Autowired
    LoginInterceptor loginInterceptor;
    private final List<String> excludes = Arrays.asList(
            "/mybatis/*",
            "/css/*",
            "/**/img/*",
            "/js/*",
            "/**/*.html",// 放行 static 文件下的内容
            "/user/login",
            "/user/reg"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(loginInterceptor)
               .addPathPatterns("/**")
               .excludePathPatterns(excludes);
    }
}
