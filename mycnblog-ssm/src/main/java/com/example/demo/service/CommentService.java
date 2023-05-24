package com.example.demo.service;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.CommentInfo;
import com.example.demo.model.Likeinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    public List<CommentInfo> selectAllByID(Integer articleID){
        return commentMapper.selectAllByID(articleID);
    }

    public List<Likeinfo> selectLike(Integer userID,Integer commentID){
        return commentMapper.selectLike(userID,commentID);
    }

    public Integer insertComment(Integer userID,String username,String content,Integer articleID){
        return commentMapper.insertComment(userID,username,content,articleID);
    }

    public Integer replyComment(Integer parentCommentID,Integer userID,String username,String content,Integer articleID){
        return commentMapper.replyComment(parentCommentID,userID,username,content,articleID);
    }

    public Integer likeComment(Integer commentID){
        return commentMapper.likeComment(commentID);
    }

    public Integer insertLike(Integer commentID,Integer userID){
        return commentMapper.insertLike(commentID,userID);
    }

    public Integer unlikeComment(Integer commentID){
        return commentMapper.unlikeComment(commentID);
    }

    public Integer deleteCommentByArticleID(Integer articleID){
        return commentMapper.deleteCommentByArticleID(articleID);
    }
    public Integer deleteLike(Integer commentID,Integer userID){
        return commentMapper.deleteLike(commentID,userID);
    }
}
