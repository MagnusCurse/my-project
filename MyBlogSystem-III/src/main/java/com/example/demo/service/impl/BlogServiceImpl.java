package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.BlogMapper;
import com.example.demo.entity.Blog;
import com.example.demo.service.IBlogService;
import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.RedisKeyUtils;
import com.example.demo.utils.SessionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {
    @Autowired
    private BlogMapper mapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发布文章功能
     * @param title
     * @param content
     * @param user_id
     * @return
     */
    public int publish(String title,String content,Integer user_id) {
        return mapper.publish(title,content,user_id);
    }

    /**
     * 根据标题查询博客 / 模糊查询
     * @param title
     * @return
     */
    public List<Blog> search(String title){
        return mapper.search(title);
    }

    /**
     * 初始化博客列表
     * @return
     */
    public List<Blog> initBlogs(){
        return mapper.initBlogs();
    }

    /**
     * 初始化个人中心博客列表
     * @param user_id
     * @return
     */
    public List<Blog> initUserBlogs(Integer user_id) {

        return mapper.initUserBlogs(user_id);
    }

    /**
     * 初始化博客内容详情
     * @param id
     * @return
     */
    public Blog initBlog(Integer id) {
        return mapper.initBlog(id);
    }

    /**
     * 修改博客内容
     * @param id
     * @param content
     * @return
     */
    public int modify(Integer id,String title,String content) {
        return mapper.modify(id,title,content);
    }

    /**
     * 删除一篇博客
     * @param id
     * @return
     */
    public int deleteBlog(Integer id) {
        return mapper.deleteBlog(id);
    }

    /**
     * 根据 id 获取一篇博客
     * @param id
     * @return
     */
    public Blog getBlog(Integer id) {
        return mapper.getBlog(id);
    }

    /**
     * 浏览博客
     * @param request
     * @param blogId
     * @return
     */
    @Override
    public Object viewBlog(HttpServletRequest request,Integer blogId) {
        // 得到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        /* 操作数据库 */
        Boolean isSuccess = update().setSql("like_count = like_count + 1").eq("id",blogId).update();
        if(!isSuccess) {
            return AjaxResult.fail(-1,"数据库更新失败");
        }
        /* Redis 操作 */
        String key = RedisKeyUtils.BLOG_VIEWED_KEY + blogId;
        // 浏览量 + 1
        stringRedisTemplate.opsForValue().increment(key);
        return AjaxResult.success(1,"浏览操作成功");
    }

    /* 初始化浏览量 */
    @Override
    public Object initViews(Integer blogId) {
        // 从 Redis 中获取浏览量数据
        String key = RedisKeyUtils.BLOG_VIEWED_KEY + blogId;
        String view = stringRedisTemplate.opsForValue().get(key);
        // Redis 中没有该记录，从数据库中查询
        if(view == null) {
            view = String.valueOf(getById(blogId).getViewCount());
            // 将数据库的内容写入 Redis
            stringRedisTemplate.opsForValue().set(key,view);
        }
        return AjaxResult.success(view,"初始化浏览量成功");
    }

    /* 初始化总页数 */
    @Override
    public Object initTotalPage(Integer pageSize) {
        List<Blog> blogs = list();
        if(blogs != null) {
            return (int) Math.ceil(blogs.size() * 1.0 / pageSize);
        }
        return 0;
    }
}
