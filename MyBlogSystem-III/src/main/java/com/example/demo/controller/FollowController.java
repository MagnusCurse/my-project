package com.example.demo.controller;

import com.example.demo.service.impl.FollowServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/follow")
public class FollowController {
    @Autowired
    FollowServiceImpl followService;
    @RequestMapping("/user")
    public Object followUser(HttpServletRequest request, Integer followUserId, Boolean isFollow) {
        return followService.followUser(request,followUserId,isFollow);
    }

    @RequestMapping("/is-follow")
    public Object isFollow(HttpServletRequest request, String followUserId) {
        return followService.isFollow(request, followUserId);
    }
}
