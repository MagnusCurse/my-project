package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService service;

    @RequestMapping("/post")
    @ResponseBody
    public Object post(HttpServletRequest request, @RequestBody Map<String,String> body) {
        String comment = body.get("comment");
        Integer blog_id = Integer.valueOf(body.get("blog_id"));
        if(!StringUtils.hasLength(comment)) {
            return AjaxResult.fail(-1,"评论为空");
        }
        // 获取当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        int res = service.post(curUser.getId(),blog_id,curUser.getUsername(),comment);
        if(res != 1) {
            return AjaxResult.fail(-1,"插入数据库失败");
        }
        return AjaxResult.success(res,"发表评论成功");
    }

    @RequestMapping("/reply")
    @ResponseBody
    public Object reply(HttpServletRequest request, @RequestBody Map<String,String> body) {
        String comment = body.get("comment");
        Integer parent_id = Integer.valueOf(body.get("parent_id"));
        Integer blog_id = Integer.valueOf(body.get("blog_id"));

        if(!StringUtils.hasLength(comment)) {
            return AjaxResult.fail(-1,"评论为空");
        }
        // 获取当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        int res = service.reply(parent_id,curUser.getId(),blog_id,curUser.getUsername(),comment);
        if(res != 1) {
            return AjaxResult.fail(-1,"插入数据库失败");
        }
        return AjaxResult.success(res,"回复评论成功");
    }

    @RequestMapping("/reply-child-comment")
    @ResponseBody
    public Object replyChildComment(HttpServletRequest request, @RequestBody Map<String,String> body) {
        String comment = body.get("comment");
        String replied_username = body.get("replied_username");
        Integer parent_id = Integer.valueOf(body.get("parent_id"));
        Integer blog_id = Integer.valueOf(body.get("blog_id"));

        if(!StringUtils.hasLength(comment) || !StringUtils.hasLength(replied_username)) {
            return AjaxResult.fail(-1,"评论或者被回复用户名为空");
        }
        // 获取当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        int res = service.replyChildComment(parent_id,curUser.getId(),blog_id,curUser.getUsername(),comment,replied_username);
        if(res != 1) {
            return AjaxResult.fail(-1,"插入数据库失败");
        }
        return AjaxResult.success(res,"回复子评论成功");
    }

    @RequestMapping("/init-parent-comment")
    @ResponseBody
    public Object initParentComment() {
      return AjaxResult.success(service.initParentComment(),"初始化父评论成功");
    }

    @RequestMapping("/init-child-comment")
    @ResponseBody
    public Object initChildComment(Integer parent_id) {
        return AjaxResult.success(service.initChildComment(parent_id),"初始化子评论成功");
    }
}
