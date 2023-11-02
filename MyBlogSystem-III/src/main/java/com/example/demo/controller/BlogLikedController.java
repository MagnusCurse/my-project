package com.example.demo.controller;

import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.SessionUnit;
import com.example.demo.entity.User;
import com.example.demo.service.impl.BlogLikedRedisService;
import com.example.demo.service.impl.BlogLikedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/blog-liked")
public class BlogLikedController {
    @Autowired
    private BlogLikedRedisService redisService;
    @Autowired
    private BlogLikedService service;

    /**
     * 没有点赞过,点赞成功数量 + 1,返回 1
     * 点赞过,取消点赞数量 - 1,返回 -1
     * @param request
     * @param likedBlogId
     * @return
     */
    @RequestMapping("/like")
    public Object likeBlog(HttpServletRequest request, String likedBlogId) {
        // 得到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        String likedPostId = String.valueOf(curUser.getId());
        String status = redisService.getStatusFromRedis(likedBlogId,likedPostId);
        // 该用户是否已经对该博客点过赞 / 注意这里要进行非空判断
        if(status != null && status.equals("1")) {
            // 该用户已经点赞过该博客, 更改该用户的点赞状态
            redisService.unlikeFromRedis(likedBlogId,likedPostId);
            // 该博客的点赞数量 - 1
            redisService.decrementLikedCount(likedBlogId);
            return AjaxResult.success("-1","取消点赞成功");
        }
        // 保存该用户对该博客的点赞记录, 更改状态为已经点赞
        redisService.saveLikedRedis(likedBlogId,likedPostId);
        // 该博客的点赞数量 + 1
        redisService.incrementLikedCount(likedBlogId);
        return AjaxResult.success("1","点赞成功");
    }

    /**
     * 初始化该博客点赞数
     * @param likedBlogId
     * @return
     */
    @RequestMapping("init-like-count")
    public Object initLikeCount(String likedBlogId) {
       String count = redisService.getLikeCountFromRedis(likedBlogId);
       // 查询结果为空, 点赞数初始化为 0
       if(count == null) {
           return AjaxResult.success(0,"初始化点赞数成功");
       }
       return AjaxResult.success(Integer.valueOf(count),"初始化点赞数量成功");
    }

}
