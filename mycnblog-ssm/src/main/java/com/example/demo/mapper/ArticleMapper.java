package com.example.demo.mapper;

import com.example.demo.model.Articleinfo;
import com.example.demo.model.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.*;

@Mapper
public interface ArticleMapper {

    public List<Articleinfo> myList(@Param("uid") Integer uid);

    public Articleinfo myDetail(@Param("id") Integer id);

    public Integer myDelete(@Param("id") Integer id);

    public Integer myUpdate(@Param("title") String title,
                            @Param("content") String content,
                            @Param("id") Integer id
                            );

    public Integer myAdd(@Param("title") String title,
                         @Param("content") String content,
                         @Param("uid") Integer uid);

    /**
     * 进行分页查询
     * @param psize
     * @param offset
     * @return
     */
    public List<Articleinfo> pageList(@Param("psize") Integer psize,@Param("offset") Integer offset);

    /**
     * 通过文章 id 查 用户 id(uid)
     * @param id
     * @return
     */
    public Integer selectUid(@Param("id") Integer id);

    /**
     * 查询所有文章
     * @return
     */
    public List<Articleinfo> selectAll();

}
