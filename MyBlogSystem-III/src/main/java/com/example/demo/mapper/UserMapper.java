package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
 public interface UserMapper extends BaseMapper<User> {
     User login(@Param("username") String username);

     int reg(@Param("username") String username,@Param("password") String password);

     int uploadAvatar(@Param("avatar_url")String avatar_url,@Param("id") Integer id);

     String initAvatar(@Param("id") Integer id);

     String initCommentAvatar(@Param("id") Integer id);

     User initUserInfo(@Param("id") Integer id);

     int changeNickname(@Param("id") Integer id,@Param("nickname") String nickname);

     int changeIntroduction(@Param("id") Integer id,@Param("introduction") String introduction);

}
