package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.BlogLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BlogLikedMapper extends BaseMapper<BlogLike> {
    /**
     * 保存点赞记录
     * @param liked_blog_id
     * @param liked_post_id
     * @return
     */
    int saveLike(@Param("liked_blog_id") String liked_blog_id,
                 @Param("liked_post_id") String liked_post_id);

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

}
