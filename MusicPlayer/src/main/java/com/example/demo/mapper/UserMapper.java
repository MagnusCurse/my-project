package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    /**
     * 根据用户用户名查询到用户对象
     * @param username
     */
    public User login(@Param("username") String username);

    /**
     * 注册,将用户名和密码插入到user表中
     * @param username
     * @param password
     */
    public void reg(@Param("username") String username,@Param("password") String password);

}
