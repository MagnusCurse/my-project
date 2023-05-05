package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public int add(String username,String password){
        return userMapper.add(username,password);
    }

    public UserInfo login(String username){
        return userMapper.login(username);
    }

    public UserInfo myContentInfo(Integer uid){
        return userMapper.myContentInfo(uid);
    }

    public String selectUsername(Integer uid){
        return userMapper.selectUsername(uid);
    }

    public String selectEmail(String username){
        return userMapper.selectEmail(username);
    }

    public int resetPassword(String username,String password){
        return userMapper.resetPassword(username,password);
    }

    public int bindEmail(String email,Integer id){
        return userMapper.bindEmail(email,id);
    }

    public int editNickname(String nickname,Integer id){
        return userMapper.editNickname(nickname,id);
    }

    public int editIntroduction(String introduction,Integer id){
        return userMapper.editIntroduction(introduction,id);
    }
}
