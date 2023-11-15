package com.example.demo.service;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.model.ArticleLikeInfo;
import com.example.demo.model.Articleinfo;
import com.example.demo.model.CommentInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    public List<Articleinfo> myList(Integer uid){
       return articleMapper.myList(uid);
    }

    public Articleinfo myDetail(Integer id){
       return articleMapper.myDetail(id);
    }

    public Integer myDelete(Integer id){
        return articleMapper.myDelete(id);
    }

    public Integer myUpdate(Integer id,String content,String title){//这里传进来的是文章id
        return articleMapper.myUpdate(title,content,id);
    }

    public Integer myAdd(Integer uid,String content,String title){
        return articleMapper.myAdd(title,content,uid);
    }

    public Integer selectUid(Integer id){
        return articleMapper.selectUid(id);
    }

    public List<Articleinfo> pageList(Integer psize, Integer offset){
        return articleMapper.pageList(psize,offset);
    }

    public Integer totalPage(Integer psize){
        List<Articleinfo> res = articleMapper.selectAll();
        if(res != null){
            return (int) Math.ceil(res.size() * 1.0 / psize);//ceil是进位函数:如 ceil(2.5) = 3
        }
        return 0;
    }

    /**
     *  点赞数量 + 1
     * @return
     */
    public Integer likeArticle(Integer articleID){
        return articleMapper.likeArticle(articleID);
    }

    /**
     * 在点赞表中添加一条记录
     * @param userID
     * @return
     */
    public Integer insertLike(Integer userID,Integer articleID){
        return articleMapper.insertLike(userID,articleID);
    }

    /**
     * 点赞数量 -1
     * @return
     */
    public Integer unlikeArticle(Integer articleID){
        return articleMapper.unlikeArticle(articleID);
    }

    /**
     * 删除点赞表中的一条记录
     * @param userID
     * @return
     */
    public Integer deleteLike(Integer userID,Integer articleID){
        return articleMapper.deleteLike(userID,articleID);
    }

    /**
     * 查询一条点赞表的记录
     * @param userID
     * @param articleID
     * @return
     */
    public List<ArticleLikeInfo> selectLike(Integer userID,Integer articleID){
        return articleMapper.selectLike(userID,articleID);
    }

    /**
     * 增加文章浏览量
     * @param id
     * @return
     */
    public Integer view(Integer id){
        return articleMapper.view(id);
    }
}
