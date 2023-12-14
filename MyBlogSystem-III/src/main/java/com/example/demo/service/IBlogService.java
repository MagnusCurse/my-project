package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Blog;

import javax.servlet.http.HttpServletRequest;

public interface IBlogService extends IService<Blog> {

    Object viewBlog(HttpServletRequest request, Integer blogId);

    Object initViews(Integer blogId);

    Object initTotalPage(Integer pageSize);
}
