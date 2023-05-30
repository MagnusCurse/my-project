package com.example.demo.mapper;

import com.example.demo.model.ArticleLikeInfo;
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

    /**
     *  点赞数量 + 1
     * @return
     */
    public Integer likeArticle(@Param("articleID") Integer articleID);

    /**
     * 在点赞表中添加一条记录
     * @param userID
     * @return
     */
    public Integer insertLike(@Param("userID") Integer userID,@Param("articleID") Integer articleID);

    /**
     * 点赞数量 -1
     * @return
     */
    public Integer unlikeArticle(@Param("articleID") Integer articleID);

    /**
     * 删除点赞表中的一条记录
     * @param userID
     * @return
     */
    public Integer deleteLike(@Param("userID") Integer userID,@Param("articleID") Integer articleID);

    /**
     * 查询一条点赞表的记录
     * @param userID
     * @param articleID
     * @return
     */
    public List<ArticleLikeInfo> selectLike(@Param("userID") Integer userID, @Param("articleID") Integer articleID);

    /**
     * 增加文章浏览量
     * @param id
     * @return
     */
    public Integer view(@Param("id") Integer id);

}
