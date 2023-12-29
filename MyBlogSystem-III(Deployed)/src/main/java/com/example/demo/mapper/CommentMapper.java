package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Comment;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
     int post(@Param("user_id") Integer user_id,
                    @Param("blog_id") Integer blog_id,
                    @Param("username") String username,
                    @Param("comment") String comment);

     int reply(@Param("parent_id") Integer parent_id,
                     @Param("user_id") Integer user_id,
                     @Param("blog_id") Integer blog_id,
                     @Param("username") String username,
                     @Param("comment") String comment);

     int replyChildComment(@Param("parent_id") Integer parent_id,
                                 @Param("user_id") Integer user_id,
                                 @Param("blog_id") Integer blog_id,
                                 @Param("username") String username,
                                 @Param("comment") String comment,
                                 @Param("replied_username") String replied_username);


     List<Comment> initParentComment(@Param("blog_id") Integer blog_id);

     List<Comment> initChildComment(@Param("parent_id") Integer parent_id,@Param("blog_id") Integer blog_id);

     int deleteComment(@Param("id") Integer id);

     int deleteChildComment(@Param("id") Integer id);
}
