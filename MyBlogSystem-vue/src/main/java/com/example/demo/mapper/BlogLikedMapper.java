package com.example.demo.mapper;

import com.example.demo.model.BlogLike;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BlogLikedMapper {
    /**
     * 保存点赞记录
     * @param blogLike
     * @return
     */
    BlogLike save(BlogLike blogLike);

    /**
     * 根据被点赞博客的 id 查询点赞列表 (即查询谁都给这篇博客点赞过)
     * @param likedBlogId
     * @param pageable
     * @return
     */
    Page<BlogLike> getLikedListByLikedUserId(String likedBlogId, Pageable pageable);

    /**
     * 根据点赞用户的 id 查询点赞列表 (即查询这个人豆豆给谁点赞过)
     * @param likedPostId
     * @param pageable
     * @return
     */
    Page<BlogLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable);

    /**
     * 通过被点赞博客的 id 和点赞用户的 id 查询点赞记录
     * @param likedBlogId
     * @param likedPostId
     * @return
     */
    BlogLike getByLikedBlogIdAndLikedPostId(String likedBlogId,String likedPostId);

    /**
     * 将 Redis 里面的点赞数据存入数据库中
     */
    void transLikedFromRedis();

    /**
     * 将 Redis 的点赞数量存入数据库中
     */
    void transLikedCountFromRedis();
}
