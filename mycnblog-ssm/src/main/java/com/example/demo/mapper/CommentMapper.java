package com.example.demo.mapper;

import com.example.demo.model.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    public List<CommentInfo> selectAllByID(@Param("articleID") Integer articleID);
}
