package com.example.demo.mapper;

import com.example.demo.model.CommentInfo;
import com.example.demo.model.CommentLikeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<CommentInfo> selectAllByID(@Param("articleID") Integer articleID);

    public Integer insertComment(@Param("userID") Integer userID,
                                 @Param("username") String username,
                                 @Param("content") String content,
                                 @Param("articleID") Integer articleID);

    public Integer replyComment(@Param("parentCommentID") Integer parentCommentID,
                                @Param("userID") Integer userID,
                                @Param("username") String username,
                                @Param("content") String content,
                                @Param("articleID") Integer articleID);

    public List<CommentLikeInfo> selectLike(@Param("userID") Integer userID, @Param("commentID") Integer commentID);

    /**
     *  点赞数量 + 1
     * @param commentID
     * @return
     */
    public Integer likeComment(@Param("commentID") Integer commentID);

    /**
     * 在点赞表中添加一条记录
     * @param commentID
     * @param userID
     * @return
     */
    public Integer insertLike(@Param("commentID") Integer commentID,@Param("userID") Integer userID,@Param("articleID") Integer articleID);

    /**
     * 点赞数量 -1
     * @param commentID
     * @return
     */
    public Integer unlikeComment(@Param("commentID") Integer commentID);

    /**
     * 删除点赞表中的一条记录
     * @param commentID
     * @param userID
     * @return
     */
    public Integer deleteLike(@Param("commentID") Integer commentID,@Param("userID") Integer userID);

    /**
     * 根据文章 ID 删除评论
     * @param articleID
     * @return
     */
    public Integer deleteCommentByArticleID(@Param("articleID") Integer articleID);

    /**
     * 根据文章 ID 删除点赞记录
     * @param articleID
     * @return
     */
    public Integer deleteLikeByArticleID(@Param("articleID") Integer articleID);


}
