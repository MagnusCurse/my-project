package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    public User login(@Param("username") String username);

    public int reg(@Param("username") String username,@Param("password") String password);

    public int uploadAvatar(@Param("avatar_url")String avatar_url,@Param("id") Integer id);

    public String initAvatar(@Param("id") Integer id);

    public String initCommentAvatar(@Param("id") Integer id);

    public User initUserInfo(@Param("id") Integer id);

    public int changeNickname(@Param("id") Integer id,@Param("nickname") String nickname);

    public int changeIntroduction(@Param("id") Integer id,@Param("introduction") String introduction);

}
