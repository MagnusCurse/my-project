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

    /**
     * 注册功能
     * @param username
     * @param password
     * @return
     */
    public int reg(String username,String password) {
        return mapper.reg(username,password);
    }

    /**
     * 上传头像
     * @param avatar_url
     * @param id
     * @return
     */
    public int uploadAvatar(String avatar_url,Integer id) {
        return mapper.uploadAvatar(avatar_url,id);
    }
}
