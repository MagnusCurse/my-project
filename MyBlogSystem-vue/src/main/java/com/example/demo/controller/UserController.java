package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SecurityUnit;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;
    @RequestMapping("/login")
    public Object login(HttpServletRequest request,String username, String password){
       if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
           return AjaxResult.fail(-1,"用户名或者密码为空");
       }
       User user = service.login(username);
       if(user == null) {
           return AjaxResult.fail(-1,"找不到该用户对象");
       }else {
           if(SecurityUnit.decrypt(password,user.getPassword())){
               // 将用户对象保存到 session 中
               SessionUnit.setLoginUser(request,user);
               return AjaxResult.success(1,"登录成功");
           }else {
               return AjaxResult.fail(-2,"登录失败,密码错误");
           }
       }
    }
}
