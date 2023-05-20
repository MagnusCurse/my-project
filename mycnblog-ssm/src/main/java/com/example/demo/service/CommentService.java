package com.example.demo.service;

import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.CommentInfo;
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

    public Integer insertComment(Integer userID,String username,String content,Integer articleID){
        return commentMapper.insertComment(userID,username,content,articleID);
    }
}
