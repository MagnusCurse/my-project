package com.example.demo.service;

import com.example.demo.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
