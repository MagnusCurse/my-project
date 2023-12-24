package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public interface IFollowService extends IService<Follow> {
    Object followUser(HttpServletRequest request, Integer followUserId, Boolean isFollow);

    Object isFollow(HttpServletRequest request,String followUserId);

    // 根据用户 id，在 follow_info 表中获取到该用户的所有关注记录的 follow_user_id
    // 根据这个 follow_user_id 初始化关注列表
    Object initFollowList(Integer userId);
}
