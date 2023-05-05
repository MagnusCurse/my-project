package com.example.demo.controller;

import com.example.demo.common.SecurityUtils;
import com.example.demo.common.SessionUtils;
import com.example.demo.model.Constant;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    SecurityUtils securityUtils;
    @Autowired
    SessionUtils sessionUtils;

    /**
     * 注册函数
     * 注册成功返回 1
     * 注册失败返回 0
     */
    @RequestMapping("/reg")
    public Object reg(HttpServletRequest request,String username,String password){
        if(StringUtils.hasLength(username) && StringUtils.hasLength(password)) {
            String finalPassword = securityUtils.encrypt(password); // 对密码进行加密
            userService.reg(username,finalPassword);
            return 1;
        }
        return 0;
    }

    /**
     * 登录函数
     * 登录成功返回 1
     * 登录失败返回 0
     */
    @RequestMapping("/login")
    public Object login(HttpServletRequest request,String username,String password){
       if(StringUtils.hasLength(username) && StringUtils.hasLength(password)){
           User user = userService.login(username);
           if(user != null){
               if(securityUtils.decrypt(password,user.getPassword())){
                   sessionUtils.setLoginUser(request,user); // 将登录信息保存到 session 中
                   return 1; //
               }
           }
       }
       return 0;
    }
}
