package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    public int publish(@Param("title") String title,@Param("content") String content,@Param("user_id") Integer user_id);
}
