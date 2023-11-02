package com.example.demo.utils;


import com.example.demo.entity.Constant;
import com.example.demo.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 操作一些与 session 相关的功能
 */
public class SessionUnit {

    /**
     * 将登录后的用户信息保存到 session 中
     * @param request
     * @param user
     */
    public static void setLoginUser(HttpServletRequest request,User user){
        // 将登录信息保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_USERINFO_KEY,user);
    }

    /**
     * 得到当前用户登录对象
     * @param request
     * @return
     */
    public static User getLoginUser(HttpServletRequest request){
        // false 表示如果当前没有 session 不会自行创建一个 session
        HttpSession session = request.getSession(false);

        if(session != null && session.getAttribute(Constant.SESSION_USERINFO_KEY) != null) {
           return (User) session.getAttribute(Constant.SESSION_USERINFO_KEY);
        }
        return null;
    }
}
