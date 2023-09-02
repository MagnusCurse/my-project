package com.example.demo.config;

import com.example.demo.common.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    private final List<String> excludes = Arrays.asList(
            "/mybatis/*",
            "/editor.md/**/*",
            "/user/login",
            "/user/init-avatar",
            "/user/reg", // 放行登录和注册接口
            "/user/logout",// 放行注销接口
            "/blog/search", // 主页查询博客接口
            "/blog/init-blogs" // 主页初始化博客列表接口

    );

    @Override
    public void addInterceptors(InterceptorRegistry registry){
         // 配置拦截器
         InterceptorRegistration registration =
                 registry.addInterceptor(loginInterceptor)
                         .addPathPatterns("/**")
                         .excludePathPatterns(excludes);
    }
}
