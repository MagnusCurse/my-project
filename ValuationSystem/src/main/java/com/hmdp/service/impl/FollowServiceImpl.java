package com.hmdp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.FollowMapper;
import com.hmdp.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.UserHolder;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;

/**
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

    @Override
    public Result follow(Long followUserId, Boolean isFollow) {
        // TODO 获取到当前用户
        UserDTO user = UserHolder.getUser();

        // TODO 判断当前用户是否已经被关注
        if(!isFollow) {
            // 没有关注,新增关注数据
            Follow follow = new Follow();
            // 设置用户 id 和被关注用户 id
            follow.setUserId(user.getId());
            follow.setFollowUserId(followUserId);
            // 将新增数据加入数据库中
            save(follow);
        } else {
            // 已经关注,取消关注
            // QueryWrapper<Follow>()：这是一个查询包装器, 用于构建数据库查询条件
            remove(new QueryWrapper<Follow>().eq("user_id",user.getId()).eq("follow_user_id",followUserId));
        }
        return Result.ok();
    }

    @Override
    public Result isFollow(Long followUserId) {
        Long userId = UserHolder.getUser().getId();
        Integer count = query().eq("user_id",userId).eq("follow_user_id",followUserId).count();
        // 关注了返回 true, 否则返回 false
        return Result.ok(count > 0);
    }
}
