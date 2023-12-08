package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.BlogLikedCount;
import com.example.demo.mapper.BlogLikedMapper;
import com.example.demo.entity.BlogLike;
import com.example.demo.utils.SessionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BlogLikedServiceImpl {
    @Autowired
    private BlogLikedRedisServiceImpl redisService;
    @Autowired
    private BlogLikedMapper mapper;

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

    /* 后面大概率不需要这些哩 */

    /**
     * 将 Redis 所有点赞数据存入数据库
     */
    public void saveLikeFromRedisToDb() {
        // 获取 Redis 中所有的点赞数据
        List<BlogLike> list = redisService.getLikedDataFromRedis();
        if(list == null) {
            return;
        }
        // 循环遍历将所有数据存入数据库中
        for (BlogLike blogLike : list) {
            String likedBlogId = blogLike.getLikedBlogId();
            String likedPostId = blogLike.getLikedPostId();
            String status = String.valueOf(blogLike.getStatus());
            // 如果当前记录不存在,则插入,否则就更新当前记录
            if(mapper.getLike(likedBlogId,likedPostId) == null) {
                mapper.saveLike(likedBlogId,likedPostId,status);
            } else {
                mapper.updateLike(likedBlogId,likedPostId,status);
            }
        }
    }

    /**
     * 将 Redis 所有点赞数存入数据库
     */
    public void saveLikeCountFromRedisToDb() {
       List<BlogLikedCount> list = redisService.getLikedCountFromRedis();
       if(list == null) {
           return;
       }
       // 循环遍历将所有点赞数存入数据库中
       for(BlogLikedCount blogLikedCount : list) {
           mapper.saveLikeCount(String.valueOf(blogLikedCount.getLikeCount()),String.valueOf(blogLikedCount.getLikedBlogId()));
       }
    }
}
