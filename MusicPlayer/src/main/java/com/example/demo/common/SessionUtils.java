package com.example.demo.common;

import com.example.demo.model.Constant;
import com.example.demo.model.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session 工具类
 */

@Component
public class SessionUtils {
    /**
     * 将登录信息保存到 session 中
     * @param request
     * @param user
     */
    public void setLoginUser(HttpServletRequest request,User user){
        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_USERINFO_KEY,user);
    }

    /**
     * 获取当前用户登录对象
     * 存在则返回,否则返回空
     * @param request
     * @return
     */
    public User getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(Constant.SESSION_USERINFO_KEY) != null){
            return (User) session.getAttribute(Constant.SESSION_USERINFO_KEY);
        }
        return null;
    }
}
