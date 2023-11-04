package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 */
public interface IBlogService extends IService<Blog> {
    Result queryBlogById(Long id);

    Result queryHotBlog(Integer current);

    Result queryBlogLikes(Long id);

    Result saveBlog(Blog blog);

    Result likeBlog(Long id);

    Result queryBlogOfFollow(Long max, Integer offset);
}
