package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.Follow;
import com.hmdp.entity.User;
import com.hmdp.mapper.FollowMapper;
import com.hmdp.service.IFollowService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.UserHolder;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;

/**
 */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IUserService userService;

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
            boolean isSuccess = save(follow);
            String key = RedisConstants.FOLLOWS_KEY + user.getId();
            // TODO 将关注用户数据保存到 Redis 中
            if(isSuccess) {
                stringRedisTemplate.opsForSet().add(key, followUserId.toString());
            }
        } else {
            // 已经关注,取消关注
            // QueryWrapper<Follow>()：这是一个查询包装器, 用于构建数据库查询条件
            boolean isSuccess = remove(new QueryWrapper<Follow>().eq("user_id",user.getId()).eq("follow_user_id",followUserId));
            // TODO 将关注数据从 Redis 中移除
            String key = RedisConstants.FOLLOWS_KEY + user.getId();
            if(isSuccess) {
                stringRedisTemplate.opsForSet().remove(key, followUserId.toString());
            }
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

    @Override
    public Result followCommon(Long id) {
        // TODO 获取当前用户
        Long curUserId = UserHolder.getUser().getId();
        // 当前用户 key
        String curkey = RedisConstants.FOLLOWS_KEY + curUserId;
        // 其它用户 key
        String othKey = RedisConstants.FOLLOWS_KEY + id;
        // TODO 从 Redis 中查询交集
        Set<String> intersect = stringRedisTemplate.opsForSet().intersect(curkey,othKey);
        if(intersect == null || intersect.isEmpty()) {
            // 返回空集合
            return Result.ok(Collections.emptyList());
        }
        // TODO 将集合转换为列表形式的用户 id 集合
        List<Long> ids = intersect.stream().map(Long :: valueOf).collect(Collectors.toList());
        // TODO 根据用户 id 集合查询用户
        List<UserDTO> users = userService.listByIds(ids)
                .stream()
                .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                .collect(Collectors.toList());
        // TODO 返回用户 dto 集合
        return Result.ok(users);
    }
}
