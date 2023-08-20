package com.example.demo.service;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper mapper;

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
    public List<Article> initBlogs(){
        return mapper.initBlogs();
    }

    /**
     * 初始化博客内容详情
     * @param id
     * @return
     */
    public Article initBlog(Integer id) {
        return mapper.initBlog(id);
    }
}
