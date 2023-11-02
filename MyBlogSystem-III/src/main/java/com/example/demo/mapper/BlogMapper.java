package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper extends BaseMapper<Blog> {
    public int publish(@Param("title") String title,@Param("content") String content,@Param("user_id") Integer user_id);

    public List<Blog> search(@Param("title") String title);

    public List<Blog> initBlogs();

    public List<Blog> initUserBlogs(@Param("user_id") Integer user_id);

    public Blog initBlog(@Param("id") Integer id);

    public Blog getBlog(@Param("id") Integer id);

    public int modify(@Param("id") Integer id,@Param("title") String title,@Param("content") String content);

    public int deleteBlog(@Param("id") Integer id);

}
