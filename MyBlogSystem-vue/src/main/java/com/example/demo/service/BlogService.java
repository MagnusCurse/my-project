package com.example.demo.service;

import com.example.demo.mapper.BlogMapper;
import com.example.demo.model.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogMapper mapper;

    /**
     * 发布文章功能
     * @param title
     * @param content
     * @param user_id
     * @return
     */
    public int publish(String title,String content,Integer user_id) {
        return mapper.publish(title,content,user_id);
    }

    /**
     * 初始化博客列表
     * @return
     */
    public List<Blog> initBlogs(){
        return mapper.initBlogs();
    }

    /**
     * 初始化博客内容详情
     * @param id
     * @return
     */
    public Blog initBlog(Integer id) {
        return mapper.initBlog(id);
    }

    /**
     * 修改博客内容
     * @param id
     * @param content
     * @return
     */
    public int modify(Integer id,String content) {
        return mapper.modify(id,content);
    }

    /**
     * 根据 id 获取一篇博客
     * @param id
     * @return
     */
    public Blog getBlog(Integer id) {
        return mapper.getBlog(id);
    }
}
