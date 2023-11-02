package com.example.demo.utils;

import lombok.Data;

/**
 * 记录被点赞博客的点赞数量和 id
 */
@Data
public class BlogLikedCount {
    private String likedBlogId;
    private Integer likeCount;

    public BlogLikedCount() {

    }
    public BlogLikedCount(String likedBlogId,Integer likeCount) {
        this.likedBlogId = likedBlogId;
        this.likeCount = likeCount;
    }
}
