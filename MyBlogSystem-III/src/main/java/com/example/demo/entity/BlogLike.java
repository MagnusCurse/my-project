package com.example.demo.entity;

import com.example.demo.utils.LikedStatusEnum;
import lombok.Data;

/**
 * 用户点赞信息
 */
@Data
public class BlogLike {
    private Integer id;
    private String likedBlogId;
    private String likedPostId;
    private Integer status = LikedStatusEnum.UNLIKE.getCode();

    public BlogLike() {

    }

    public BlogLike(String likedBlogId,String likedPostId,Integer status) {
        this.likedBlogId = likedBlogId;
        this.likedPostId = likedPostId;
        this.status = status;
    }
}
