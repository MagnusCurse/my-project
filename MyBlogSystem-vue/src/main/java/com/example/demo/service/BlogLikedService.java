package com.example.demo.service;

import com.example.demo.mapper.BlogLikedMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }

    /**
     * 将 Redis 所有点赞数存入数据库
     */
    public void saveLikeCountFromRedisToDb() {

    }
}
