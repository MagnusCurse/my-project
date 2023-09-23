package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private IUserService userService;

    @Override
    public Result queryBlogById(Long id) {
        // TODO 查询博客
        Blog blog = getById(id);
        if(blog == null) {
            return Result.fail("查询博客为空");
        }
        // TODO 查询博客相关用户信息
        queryUserById(blog);
        // TODO 将博客对象返回
        return Result.ok(blog);
    }

    private void queryUserById(Blog blog) {
        // TODO 根据 id 在数据库中查询到用户
        User user = userService.getById(blog.getId());
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }
}
