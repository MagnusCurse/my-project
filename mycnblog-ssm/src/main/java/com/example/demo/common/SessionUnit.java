package com.example.demo.common;


import com.example.demo.model.Constant;
import com.example.demo.model.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 操作一些与 session 相关的功能
 */
public class SessionUnit {

    /**
     * 将登录后的用户信息保存到 session 中
     * @param request
     * @param userInfo
     */
    public static void setLoginUser(HttpServletRequest request,UserInfo userInfo){
        //将登录信息保存到 session 中
        HttpSession session = request.getSession();
        session.setAttribute(Constant.SESSION_USERINFO_KEY,userInfo);
    }

    /**
     * 得到当前用户登录对象
     * @param request
     * @return
     */
    public static UserInfo getLoginUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute(Constant.SESSION_USERINFO_KEY) != null) {
           return (UserInfo) session.getAttribute(Constant.SESSION_USERINFO_KEY);
        }
        return null;
    }
}
