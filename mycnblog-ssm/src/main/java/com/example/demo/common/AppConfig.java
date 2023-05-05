package com.example.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/**
 * 拦截器系统配置
 */

@Configuration
public class AppConfig implements WebMvcConfigurer{
    @Autowired
    private LoginInterceptor loginInterceptor;
    private final List<String> excludes = Arrays.asList(
            "/mybatis/*",
            "/css/*",
            "/editor.md/**/*",
            "/img/*",
            "/js/*",
            "/**/*.html",//放行static文件下的内容
            "/user/login",
            "/user/reg",//放行登录和注册接口
            "/article/detail",//放行博客详情页面
            "/user/mycontentinfo",//放行博客详情页个人信息初始化
            "/article/list",//放开初始化主列表接口
            "/article/total",//放行计算总页数接口
            "/article/comment",//放开初始化评论接口
            "/retrieve/code",//放开发送验证码接口
            "/retrieve/resetpassword"//放开重置密码接口
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //配置拦截器
        InterceptorRegistration registration =
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludes);
    }
}
