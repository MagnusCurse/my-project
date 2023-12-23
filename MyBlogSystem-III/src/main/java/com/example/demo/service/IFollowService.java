package com.example.demo.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Follow;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

public interface IFollowService extends IService<Follow> {
    Object followUser(HttpServletRequest request, Integer followUserId, Boolean isFollow);

    Object isFollow(HttpServletRequest request,String followUserId);
}
