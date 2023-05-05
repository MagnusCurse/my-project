package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 根据用户用户名查询到用户对象
     * @param username
     */
    public User login(String username){
       User user = userMapper.login(username);
       return user;
    }

    /**
     * 注册,将用户名和密码插入到user表中
     * @param username
     * @param password
     */
    public void reg(String username,String password){
        userMapper.reg(username,password);
    }
}
