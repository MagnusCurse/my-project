package com.example.demo.service.impl;

import com.example.demo.mapper.UserMapper;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {
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

    /**
     * 初始化用户头像
     * @param id
     * @return
     */
    public String initAvatar(Integer id) {
        return mapper.initAvatar(id);
    }

    /**
     * 初始化用户评论头像
     * @param id
     * @return
     */
    public String initCommentAvatar(Integer id) {
        return mapper.initCommentAvatar(id);
    }

    /**
     * 初始化用户信息
     * @param id
     * @return
     */
    public User initUserInfo(Integer id) {
        return mapper.initUserInfo(id);
    }

    /**
     * 修改昵称
     * @param id
     * @return
     */
    public int changeNickname(Integer id,String nickname) {
        return mapper.changeNickname(id,nickname);
    }

    /**
     * 修改简介
     * @param id
     * @return
     */
    public int changeIntroduction(Integer id,String introduction) {
        return mapper.changeIntroduction(id,introduction);
    }


}
