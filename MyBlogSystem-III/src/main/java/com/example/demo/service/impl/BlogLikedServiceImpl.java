package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Blog;
import com.example.demo.entity.User;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.service.IBlogLikedService;
import com.example.demo.service.IBlogService;
import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.BlogLikedCount;
import com.example.demo.mapper.BlogLikedMapper;
import com.example.demo.entity.BlogLike;
import com.example.demo.utils.RedisKeyUtils;
import com.example.demo.utils.SessionUnit;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class BlogLikedServiceImpl extends ServiceImpl<BlogLikedMapper,BlogLike> implements IBlogLikedService {
    @Autowired
    private BlogLikedRedisServiceImpl redisService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private BlogServiceImpl blogService;
    @Autowired
    private BlogLikedMapper mapper;

    /**
     * 点赞成功返回 1，失败返回 -1
     * @param request
     * @param likedBlogId
     * @return
     */
    // 添加事务，当数据库操作失败时候回滚
    @Transactional
    public Object likeBlog(HttpServletRequest request,String likedBlogId) {
        // 得到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        // 创建 Redis key: RedisKey + 博客ID
        String key = RedisKeyUtils.BLOG_LIKED_KEY + likedBlogId;
        // 判断当前用户是否点赞过
        /* 这里返回的是一个 Boolean, 可能为空, 这样可以防止空指针异常 */
        boolean liked = Boolean.TRUE.equals(stringRedisTemplate.opsForSet().isMember(key, curUser.getId().toString()));
        // 如果该用户已经点赞过
        if(liked) {
            /* 数据库操作 */
            // 点赞数量 - 1
            boolean isSuccess = blogService.update().setSql("like_count = like_count - 1").eq("id",likedBlogId).update();
            if(!isSuccess) {
                return AjaxResult.fail(-1,"数据库更新失败");
            }
            // 移除数据库中该条记录
            QueryWrapper<BlogLike> wrapper = new QueryWrapper<>();
            wrapper.eq("blog_id",likedBlogId);
            wrapper.eq("user_id",curUser.getId());
            remove(wrapper);

            /* Redis 操作 */
            // 移除 set 中的该条记录
            stringRedisTemplate.opsForSet().remove(key,curUser.getId().toString());
        } else {
            // 用户没有点赞过
            // 点赞数量 + 1
            boolean isSuccess = blogService.update().setSql("like_count = like_count + 1").eq("id",likedBlogId).update();
            if(!isSuccess) {
                return AjaxResult.fail(-1,"数据库更新失败");
            }
            // 新增一条点赞记录
            /* ERR 这里一直报类型错误，暂时不知道什么原因，用传统的  MyBatis 好了 */
            // BlogLike blogLike = new BlogLike(Integer.valueOf(likedBlogId),curUser.getId());
            // save(blogLike);
            mapper.saveLike(likedBlogId,curUser.getId().toString());

            /* Redis 操作 */
            // 新增 set 一条记录
            stringRedisTemplate.opsForSet().add(key,curUser.getId().toString());
        }
        return AjaxResult.success(1,"用户点赞成功");
    }

    /**
     * 初始化该博客点赞数
     * @param likedBlogId
     * @return
     */
    public Object initLikeCount(String likedBlogId) {
        // 从 Redis 中获取到点赞数据
        String key = RedisKeyUtils.BLOG_LIKED_KEY + likedBlogId;
        Long count = stringRedisTemplate.opsForSet().size(key);
        // 如果 Redis 中获取不到，则操作数据库
        if(count == null) {
            count = (long) blogService.getById(likedBlogId).getLikes();
            /* 将数据库的数据重新写入 Redis 中 */


        }
        return AjaxResult.success(count,"初始化博客点赞数成功");
    }
}
