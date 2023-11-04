package com.hmdp.utils;

import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO 从 ThreadLocal 中获取用户, 如果没有则拦截
        if(UserHolder.getUser() == null) {
            response.setStatus(401);
            return false;
        }
        // TODO 放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       // TODO 将用户从 ThreadLocal 中移除
       UserHolder.removeUser();
    }
}
