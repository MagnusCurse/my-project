package com.example.demo.mapper;

import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public int post(@Param("user_id") Integer user_id,
                    @Param("blog_id") Integer blog_id,
                    @Param("username") String username,
                    @Param("comment") String comment);

    public int reply(@Param("parent_id") Integer parent_id,
                     @Param("user_id") Integer user_id,
                     @Param("blog_id") Integer blog_id,
                     @Param("username") String username,
                     @Param("comment") String comment);

    public int replyChildComment(@Param("parent_id") Integer parent_id,
                                 @Param("user_id") Integer user_id,
                                 @Param("blog_id") Integer blog_id,
                                 @Param("username") String username,
                                 @Param("comment") String comment,
                                 @Param("replied_username") String replied_username);


    public List<Comment> initParentComment(@Param("blog_id") Integer blog_id);

    public List<Comment> initChildComment(@Param("parent_id") Integer parent_id,@Param("blog_id") Integer blog_id);

    public int deleteComment(@Param("id") Integer id);

    public int deleteChildComment(@Param("id") Integer id);
}
