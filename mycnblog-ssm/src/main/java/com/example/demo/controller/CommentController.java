package com.example.demo.controller;

import com.example.demo.common.SessionUnit;
import com.example.demo.model.CommentInfo;
import com.example.demo.model.UserInfo;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/comment")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    /**
     * 根据文章 ID 查询该文章的所有评论
     * @return
     */
    @RequestMapping("/search")
    public List<CommentInfo> selectComment(Integer articleID){
       return commentService.selectAllByID(articleID);
    }

    /**
     * 发表评论功能
     * @param articleID
     * @param content
     * @return
     */
    @RequestMapping("/submit")
    public Integer submitComment(String content,Integer articleID,HttpServletRequest request){
        UserInfo userInfo = SessionUnit.getLoginUser(request);// 获取到当前用户对象
        String username = userInfo.getUsername();// 获取到当前用户名
        Integer userID = userInfo.getId();
        return commentService.insertComment(userID,username,content,articleID);
    }
}
