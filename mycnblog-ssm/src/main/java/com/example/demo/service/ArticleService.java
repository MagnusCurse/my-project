package com.example.demo.service;

import com.example.demo.mapper.ArticleMapper;
import com.example.demo.model.Articleinfo;
import com.example.demo.model.CommentInfo;
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

    public List<CommentInfo> selectComment(Integer aid){
        return articleMapper.selectComment(aid);
    }

    public Integer submitComment(Integer aid,String content,String username){
        return articleMapper.insertComment(aid,content,username);
    }
}
