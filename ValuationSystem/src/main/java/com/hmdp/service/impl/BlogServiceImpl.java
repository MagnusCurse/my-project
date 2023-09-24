package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
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
import java.util.Set;
import java.util.stream.Collectors;

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
        // TODO 获取当前用户对象
        UserDTO user = UserHolder.getUser();
        // 如果当前用户没有登录则不需要进行操作
        if(user == null) {
            return;
        }
        // TODO 获取当前用户 id
        Long userId = user.getId();
        // TODO 判断当前用户是否已经点赞
        String key = RedisConstants.BLOG_LIKED_KEY + blog.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        // TODO 更新 isLiked 字段是值
        blog.setIsLike(BooleanUtil.isTrue(score != null));
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
    public Result queryBlogLikes(Long id) {
        // TODO 查询该博客的用户 id 前五个
        Set<String> topFive = stringRedisTemplate.opsForZSet().range(RedisConstants.BLOG_LIKED_KEY + id,0,4);
        if(topFive == null) {
            return Result.ok();
        }
        // TODO 将其转换为 Long 整数列表
        // .collect(Collectors.toList()): 这是一个终结操作, 它将流中的元素收集到一个新的 List 中
        List<Long> ids = topFive.stream().map(Long :: valueOf).collect(Collectors.toList());
        // NOTE 注意这里不能把空数组传入 userService.listByIds(ids)
        if(ids.size() == 0) {
            return Result.ok();
        }
        // 将 id 用逗号分割并转换为一个字符串
        String idStr = StrUtil.join(",",ids);
        // TODO 根据用户 id 查询用户
        // NOTE 这里不能用原来的 listByIds 去查询,因为结果不是顺序的
        List<UserDTO> users = userService.query().in("id",ids)
                .last("ORDER BY FIELD(id," + idStr + ")").list()
                // .map(user -> BeanUtil.copyProperties(user, UserDTO.class)): 这是一个流操作，它将每个用户对象转换为 UserDTO 对象。
                // 在这里使用了一个 lambda 表达式，对每个用户对象执行了 BeanUtil.copyProperties 方法，将用户对象的属性复制到一个新的 UserDTO 对象中
                .stream()
                .map(user -> BeanUtil.copyProperties(user,UserDTO.class))
                .collect(Collectors.toList());
        // TODO 返回用户数据
        return Result.ok(users);
    }

    @Override
    public Result likeBlog(Long id) {
       // TODO 获取当前用户 id
       Long userId = UserHolder.getUser().getId();
       // TODO 判断当前用户是否已经点赞
       String key = RedisConstants.BLOG_LIKED_KEY + id;
       Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
       // 当前用户没有点过赞
       if(score == null) {
          // 数据库点赞数 + 1
          boolean isSuccess = update().setSql("liked = liked + 1").eq("id", id).update();
          if(!isSuccess) {
              return Result.fail("数据库点赞 + 1 操作失败");
          }
          // 将点赞记录保存到 Redis 的集合
          // System.currentTimeMillis(): 这是成员的分数。在有序集合中，每个成员都有一个分数，用于确定成员的排名顺序
          stringRedisTemplate.opsForZSet().add(key,userId.toString(),System.currentTimeMillis());
       } else { // 当前用户已经点赞
            // 数据库点赞数量 - 1
            boolean isSuccess = update().setSql("liked = liked - 1").eq("id", id).update();
            if(!isSuccess) {
                return Result.fail("数据库点赞 - 1 操作失败");
            }
            // 清楚 Redis 中的点赞记录
            stringRedisTemplate.opsForZSet().remove(key,userId.toString());
       }
        return Result.ok();
    }
}
