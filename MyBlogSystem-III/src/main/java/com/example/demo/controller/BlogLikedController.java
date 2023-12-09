package com.example.demo.controller;


import com.example.demo.service.impl.BlogLikedRedisServiceImpl;
import com.example.demo.service.impl.BlogLikedServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/blog-liked")
public class BlogLikedController {
    @Autowired
    private BlogLikedRedisServiceImpl redisService;
    @Autowired
    private BlogLikedServiceImpl service;

    /**
     * 没有点赞过,点赞成功数量 + 1,返回 1
     * 点赞过,取消点赞数量 - 1,返回 -1
     * @param request
     * @param likedBlogId
     * @return
     */
    @RequestMapping("/like")
    public Object likeBlog(HttpServletRequest request, String likedBlogId) {
        return service.likeBlog(request,likedBlogId);
    }

    /**
     * 初始化该博客点赞数
     * @param likedBlogId
     * @return
     */
    @RequestMapping("init-like-count")
    public Object initLikeCount(String likedBlogId) {
       return service.initLikeCount(likedBlogId);
    }
}
