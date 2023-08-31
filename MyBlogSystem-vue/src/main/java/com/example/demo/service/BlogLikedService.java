package com.example.demo.service;

import com.example.demo.common.BlogLikedCount;
import com.example.demo.mapper.BlogLikedMapper;
import com.example.demo.model.BlogLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogLikedService {
    @Autowired
    private BlogLikedRedisService redisService;
    @Autowired
    private BlogLikedMapper mapper;

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
