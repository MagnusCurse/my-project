package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;

    /**
     * 登录功能
     * @param username
     * @return
     */
    public User login(String username) {
        return mapper.login(username);
    }
}
