package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.service.IFollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 */
@RestController
@RequestMapping("/follow")
public class FollowController {
    @Resource
    IFollowService followService;

    /**
     * 关注用户功能
     * @param followUserId
     * @param isFollow
     * @return
     */
    @PutMapping("/{id}/{isFollow}")
    public Result follow(@PathVariable("id") Long followUserId, @PathVariable("isFollow") Boolean isFollow) {
        return followService.follow(followUserId,isFollow);
    }

    /**
     * 判断是否已经关注
     * @param followUserId
     * @return
     */
    @GetMapping("/or/not/{id}")
    public Result isFollow(@PathVariable("id") Long followUserId) {
        return followService.isFollow(followUserId);
    }

    /**
     * 查询与当前用户的共同关注列表
     * @param id
     * @return
     */
    @GetMapping("/common/{id}")
    public Result followCommon(@PathVariable("id") Long id) {
        return followService.followCommon(id);
    }
}
