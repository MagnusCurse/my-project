package com.example.demo.service;

import com.example.demo.common.BlogLikedCount;
import com.example.demo.common.LikedStatusEnum;
import com.example.demo.common.RedisKeyUtils;
import com.example.demo.model.BlogLike;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class BlogLikedRedisService {
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
     * 获取 Redis 中所有的点赞数据
     * @return
     */
    public List<BlogLike> getLikedDataFromRedis() {
        // Cursor: 这是用于接收扫描结果的游标（Cursor）。游标是一种迭代器，允许你逐步遍历大型数据集，而不需要一次性加载全部数据到内存中。
        // scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE): 这是在 Hash 数据结构中进行扫描的操作。它使用了 scan 方法，该方法允许你迭代遍历一个 Hash 中的元素。
        // ScanOptions.NONE：这是一个扫描选项对象，用于指定扫描的参数。在这种情况下，使用了 NONE，表示不使用任何特定的扫描选项。
        // Map.Entry 是 Map 接口中的一个嵌套接口，用于表示单个键值对。
        Cursor<Map.Entry<Object,Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<BlogLike> res = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            // 分离出 likedBlogId 和 likedPostId
            String[] split = key.split("::");
            String likedBlogId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();

            // 组装成 BlogLike 对象
            BlogLike blogLike = new BlogLike(likedBlogId, likedPostId, value);
            res.add(blogLike);

            // 从 Redis 中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED,key);
        }
        return res;
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
            BlogLikedCount count = new BlogLikedCount(key, (Integer) entry.getValue());
            res.add(count);

            // 从 Redis 中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_BLOG_LIKED_COUNT, key);
        }
        return res;
    }
}
