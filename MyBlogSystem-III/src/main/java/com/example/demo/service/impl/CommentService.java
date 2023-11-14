package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.entity.Comment;
import com.example.demo.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper mapper;

    /**
     * 发表评论功能
     * @param user_id
     * @param blog_id
     * @param username
     * @param comment
     * @return
     */
    public int post(Integer user_id,Integer blog_id,String username,String comment) {
      return mapper.post(user_id,blog_id,username,comment);
    }

    /**
     * 回复评论功能
     * @param parent_id
     * @param user_id
     * @param blog_id
     * @param username
     * @param comment
     * @return
     */
    public int reply(Integer parent_id,Integer user_id,Integer blog_id,String username,String comment) {
        return mapper.reply(parent_id,user_id,blog_id,username,comment);
    }

    /**
     * 回复子评论功能
     * @param parent_id
     * @param user_id
     * @param blog_id
     * @param username
     * @param comment
     * @param replied_username
     * @return
     */
    public int replyChildComment(Integer parent_id,Integer user_id,Integer blog_id,String username,String comment,String replied_username) {
        return mapper.replyChildComment(parent_id,user_id,blog_id,username,comment,replied_username);
    }

    /**
     * 初始化父评论元素
     * @return
     */
    public List<Comment> initParentComment(Integer blog_id) {
        /* 查询评论字段为 null */
        List<Comment> ret = mapper.initParentComment(blog_id);
        return mapper.initParentComment(blog_id);
    }

    /**
     * 初始化子评论元素
     * @param parent_id
     * @return
     */
    public List<Comment> initChildComment(Integer parent_id,Integer blog_id) {
        return mapper.initChildComment(parent_id,blog_id);
    }

    /**
     * 根据 id 删除一条评论
     * @param id
     * @return
     */
    public int deleteComment(Integer id) {
        return mapper.deleteComment(id);
    }

    /**
     * 删除父评论下的子评论
     * @param id
     * @return
     */
    public int deleteChildComment(Integer id) {
        return mapper.deleteChildComment(id);
    }
}
