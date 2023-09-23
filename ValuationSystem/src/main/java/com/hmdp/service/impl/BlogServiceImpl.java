package com.hmdp.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.entity.Blog;
import com.hmdp.entity.User;
import com.hmdp.mapper.BlogMapper;
import com.hmdp.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.SystemConstants;
import com.hmdp.utils.UserHolder;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IUserService userService;
    @Resource
    private IBlogService blogService;

    /**
     * 设置博客用户相关信息
     * @param blog
     */
    private void queryUserById(Blog blog) {
        // TODO 根据 id 在数据库中查询到用户
        User user = userService.getById(blog.getId());
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }

    /**
     * 判断博客是否被点赞过, 并初始化博客的 isLiked
     * @param blog
     */
    private void isBlogLiked(Blog blog) {
        // TODO 获取当前用户 id
        Long userId = UserHolder.getUser().getId();
        // TODO 判断当前用户是否已经点赞
        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();
        Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId.toString());
        // TODO 更新 isLiked 字段是值
        blog.setIsLike(BooleanUtil.isTrue(isMember));
    }

    @Override
    public Result queryBlogById(Long id) {
        // TODO 查询博客
        Blog blog = getById(id);
        if(blog == null) {
            return Result.fail("查询博客为空");
        }
        // TODO 查询博客相关用户信息
        queryUserById(blog);
        // TODO 判断博客是否被点赞过, 即对 isLiked 进行初始化
        isBlogLiked(blog);
        // TODO 将博客对象返回
        return Result.ok(blog);
    }

    @Override
    public Result queryHotBlog(Integer current) {
        // 根据用户查询
        Page<Blog> page = blogService.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE)); //
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog ->{
            this.queryUserById(blog);
            this.isBlogLiked(blog);
        });
        return Result.ok(records);
    }

    @Override
    public Result likeBlog(Long id) {
       // TODO 获取当前用户 id
       Long userId = UserHolder.getUser().getId();
       // TODO 判断当前用户是否已经点赞
       String key = RedisConstants.BLOG_LIKED_KEY + id;
       Boolean isMember = stringRedisTemplate.opsForSet().isMember(key, userId.toString());
       // 当前用户没有点过赞
       if(BooleanUtil.isFalse(isMember)) {
          // 数据库点赞数 + 1
          boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
          if(!isSuccess) {
              return Result.fail("数据库点赞 + 1 操作失败");
          }
          // 将点赞记录保存到 Redis 的集合
          stringRedisTemplate.opsForSet().add(key,userId.toString());

       } else { // 当前用户已经点赞
            // 数据库点赞数量 - 1
            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
            if(!isSuccess) {
                return Result.fail("数据库点赞 - 1 操作失败");
            }
            // 清楚 Redis 中的点赞记录
            stringRedisTemplate.opsForSet().remove(key,userId.toString());
       }
        return Result.ok();
    }
}
