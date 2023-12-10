package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Blog;

import javax.servlet.http.HttpServletRequest;

public interface IBlogService extends IService<Blog> {
    public Object viewBlog(HttpServletRequest request, Integer blogId);

    public Object initViews(Integer blogId);

    public Object initTotalPage(Integer pageSize);
}
