package com.example.demo.mapper;

import com.example.demo.model.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    public int publish(@Param("title") String title,@Param("content") String content,@Param("user_id") Integer user_id);

    public List<Blog> initBlogs();

    public Blog initBlog(@Param("id") Integer id);

    public Blog getBlog(@Param("id") Integer id);

    public int modify(@Param("id") Integer id,@Param("content") String content);

}
