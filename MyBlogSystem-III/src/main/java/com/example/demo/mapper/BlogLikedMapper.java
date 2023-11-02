package com.example.demo.mapper;

import com.example.demo.entity.BlogLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogLikedMapper {
    /**
     * 保存点赞记录
     * @param liked_blog_id
     * @param liked_post_id
     * @param status
     * @return
     */
    int saveLike(@Param("liked_blog_id") String liked_blog_id,
                 @Param("liked_post_id") String liked_post_id,
                 @Param("status") String status);

    /**
     * 更新点赞记录
     * @param liked_blog_id
     * @param liked_post_id
     * @param status
     * @return
     */
    int updateLike(@Param("liked_blog_id") String liked_blog_id,
                   @Param("liked_post_id") String liked_post_id,
                   @Param("status") String status);

    /**
     * 得到一条点赞记录
     * @param liked_blog_id
     * @param liked_post_id
     * @return
     */
    BlogLike getLike(@Param("liked_blog_id") String liked_blog_id, @Param("liked_post_id") String liked_post_id);

    /**
     * 根据博客 id 保存点赞数
     * @param like_count
     * @param liked_blog_id
     * @return
     */
    int saveLikeCount(@Param("like_count") String like_count, @Param("liked_blog_id") String liked_blog_id);

//    /**
//     * 根据被点赞博客的 id 查询点赞列表 (即查询谁都给这篇博客点赞过)
//     * @param likedBlogId
//     * @param pageable
//     * @return
//     */
//    Page<BlogLike> getLikedListByLikedUserId(String likedBlogId, Pageable pageable);
//
//    /**
//     * 根据点赞用户的 id 查询点赞列表 (即查询这个人都给谁点赞过)
//     * @param likedPostId
//     * @param pageable
//     * @return
//     */
//    Page<BlogLike> getLikedListByLikedPostId(String likedPostId, Pageable pageable);
//
//    /**
//     * 通过被点赞博客的 id 和点赞用户的 id 查询点赞记录
//     * @param likedBlogId
//     * @param likedPostId
//     * @return
//     */
//    BlogLike getByLikedBlogIdAndLikedPostId(String likedBlogId,String likedPostId);
//
//    /**
//     * 将 Redis 里面的点赞数据存入数据库中
//     */
//    void transLikedFromRedis();
//
//    /**
//     * 将 Redis 的点赞数量存入数据库中
//     */
//    void transLikedCountFromRedis();

}
