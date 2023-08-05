package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/login")
    public Object login(HttpServletRequest request,String username, String password){
       if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
           return AjaxResult.fail(-1,"用户名或者密码为空");
       }

    }
}
