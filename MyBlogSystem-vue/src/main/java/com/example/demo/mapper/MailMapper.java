package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MailMapper {
    public int changeEmail(@Param("id") Integer id, @Param("mail") String mail);

    public int retrievePassword(@Param("username") String username, @Param("password") String password);

    public String retrieveSend(@Param("username") String username);
}
