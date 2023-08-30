package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.User;
import com.example.demo.service.BlogLikedRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/blog-liked")
public class BlogLikedController {
    @Autowired
    private BlogLikedRedisService redisService;

    @RequestMapping("/like")
    public Object likeBlog(HttpServletRequest request, String likedBlogId) {
        // 得到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        String likedPostId = String.valueOf(curUser.getId());
        // 保存该用户对该博客的点赞记录, 状态记录未已经点赞
        redisService.saveLikedRedis(likedBlogId,likedPostId);
        // 该博客的点赞数量 + 1
        redisService.incrementLikedCount(likedBlogId);
        return AjaxResult.success("","点赞成功");
    }
}
