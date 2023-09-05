package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MailMapper {
    public int changeEmail(@Param("id") Integer id, @Param("mail") String mail);
}
