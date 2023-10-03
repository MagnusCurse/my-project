package com.hmdp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsConfig implements WebMvcConfigurer,Filter {

    // 解决前端跨域无法给后端发送请求问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**") // 所有接口
               .allowCredentials(true) // 是否发送 Cookie
               .allowedOrigins("*") // 支持域
               .allowedMethods("*") // 允许所有方法
               .allowedHeaders("*") // 允许所有请求头
               .exposedHeaders("*");
    }

    // 解决跨域 Session 丢失问题
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // System.out.println("CorsFilter doFilter...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        // 允许请求携带认证信息(cookie)
        res.setHeader("Access-Control-Allow-Credentials", "true");
        // 指定允许其他域名访问
        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        // 允许请求的类型
        res.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        // 允许的请求头字段
        res.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        // 设置预检请求的有效期
        // 浏览器同源策略：出于安全考虑，浏览器限制跨域的 http 请求。怎样限制呢？通过发送两次请求：预检请求、用户请求。
        // 1、预检请求作用：获知服务器是否允许该跨域请求：如果允许，才发起第二次真实的请求；如果不允许，则拦截第二次请求
        // 2、单位:s,在此期间不用发送预检请求。
        // 3、若为0：表示每次请求都发送预检请求,每个ajax请求之前都会先发送预检请求。
        res.setHeader("Access-Control-Max-Age", "3600");
        // OPTIONS Method表示浏览器发送的预检请求。
        if ("OPTIONS".equalsIgnoreCase(req.getMethod())) {
            res.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }
}
