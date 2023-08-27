package com.example.demo.service;

import com.example.demo.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentMapper mapper;

    /**
     * 发表评论功能
     * @param user_id
     * @param blog_id
     * @param username
     * @param comment
     * @return
     */
    public int post(Integer user_id,Integer blog_id,String username,String comment) {
      return mapper.post(user_id,blog_id,username,comment);
    }

    /**
     * 回复评论功能
     * @param parent_id
     * @param user_id
     * @param blog_id
     * @param username
     * @param comment
     * @return
     */
    public int reply(Integer parent_id,Integer user_id,Integer blog_id,String username,String comment) {
        return mapper.reply(parent_id,user_id,blog_id,username,comment);
    }
}
