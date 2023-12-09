package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 用户点赞信息
 */
@Data
@TableName("blog_like_info")
public class BlogLike {
    private Integer id;
    private Integer likedBlogId;
    private Integer likedPostId;
    private Date createTime;
    private Date updateTime;

    public BlogLike() {

    }

    public BlogLike(Integer likedBlogId,Integer likedPostId) {
        this.likedBlogId = likedBlogId;
        this.likedPostId = likedPostId;
    }
}
