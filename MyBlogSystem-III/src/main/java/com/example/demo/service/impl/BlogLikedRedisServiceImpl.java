package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.BlogLikedMapper;
import com.example.demo.service.IBlogLikedService;
import com.example.demo.utils.BlogLikedCount;
import com.example.demo.utils.LikedStatusEnum;
import com.example.demo.utils.RedisKeyUtils;
import com.example.demo.entity.BlogLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlogLikedRedisServiceImpl {
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 点赞。状态为 1
     * @param likedBlogId
     * @param likedPostId
     */
    public void saveLikedRedis(String likedBlogId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId,likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,String.valueOf(LikedStatusEnum.LIKE.getCode()));
    }

    /**
     * 查询当前用户点赞状态
     * @param likedBlogId
     * @param likedPostId
     * @return
     */
    public String getStatusFromRedis(String likedBlogId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId,likedPostId);
        return (String) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }

    /**
     * 取消点赞。将状态改变为 0
     * @param likedBlogId
     * @param likedPostId
     */
    public void unlikeFromRedis(String likedBlogId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId,likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,String.valueOf(LikedStatusEnum.UNLIKE.getCode()));
    }

    /**
     * 从 Redis 中删除一条点赞数据
     * @param likedBlogId
     * @param likedPostId
     */
    public void deleteLikedFromRedis(String likedBlogId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedBlogId,likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
    }

    /**
     * 该博客的点赞数 + 1
     * @param likedBlogId
     */
    public void incrementLikedCount(String likedBlogId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT,likedBlogId,1);
    }

    /**
     * 该博客的点赞数 - 1
     * @param likedBlogId
     */
    public void decrementLikedCount(String likedBlogId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT,likedBlogId,-1);
    }

    /**
     * 获得该博客的点赞数量
     * @param likedBlogId
     * @return
     */
    public String getLikeCountFromRedis(String likedBlogId) {
        return (String) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT,likedBlogId);
    }



    /**
     * 获取 Redis 中每篇博客的点赞数量
     * @return
     */
    public List<BlogLikedCount> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT, ScanOptions.NONE);
        List<BlogLikedCount> res = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();

            // 组装成 BlogLike 对象
            BlogLikedCount count = new BlogLikedCount(key, Integer.valueOf((String) entry.getValue()));
            res.add(count);
        }
        return res;
    }
}
