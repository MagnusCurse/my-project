package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Follow;
import com.example.demo.entity.User;
import com.example.demo.mapper.FollowMapper;
import com.example.demo.service.IFollowService;
import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.RedisKeyUtils;
import com.example.demo.utils.SessionUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 关注用户功能
     * @param request
     * @param followUserId
     * @param isFollow
     * @return
     */
    @Override
    @Transactional
    public Object followUser(HttpServletRequest request, Integer followUserId, Boolean isFollow) {
        // 获取到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户不存在");
        }
        // 判断当前用户是否已经被关注
        if(!isFollow) {
            /* 操作数据库 */
            Follow follow = new Follow();
            follow.setUserId(curUser.getId());
            follow.setFollowUserId(followUserId);
            Boolean isSuccess = save(follow);
            /* 操作 Redis */
            String key = RedisKeyUtils.USER_FOLLOWED_KEY + curUser.getId();
            if(isSuccess) {
                stringRedisTemplate.opsForZSet().add(key,followUserId.toString(),System.currentTimeMillis());
            }
        } else { // 用户已经关注，则取消关注
            // 移除数据库中该条关注记录
            Boolean isSuccess = remove(new QueryWrapper<Follow>().eq("user_id",curUser.getId()).eq("followed_user_id",followUserId));
            String key = RedisKeyUtils.USER_FOLLOWED_KEY + curUser.getId();
            if(isSuccess) {
                stringRedisTemplate.opsForZSet().remove(key,followUserId);
            }
        }
        return AjaxResult.success(1,"关注 / 取关成功");
    }

    /**
     * 判断当前用户是否有关注该用户
     * @param request
     * @param followUserId
     * @return
     */
    @Override
    public Object isFollow(HttpServletRequest request, Integer followUserId) {
        // 获取到当前用户对象
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户不存在");
        }
        /* 从 Redis 中查询 */
        String key = RedisKeyUtils.USER_FOLLOWED_KEY + curUser.getId();
        Double isFollow = stringRedisTemplate.opsForZSet().score(key,followUserId);
        // isFollow 为空即证明 Redis 中没有该条数据
        if(isFollow == null) {
            /* 从数据库中查询 */
            Integer count = query().eq("user_id",curUser.getId()).eq("followed_user_id",followUserId).count();
            // 获取到数据库该关注记录的创建时间
            Date createTime = query().eq("user_id",curUser.getId()).eq("followed_user_id",followUserId).one().getCreateTime();
            if(count > 0) {
                // 将数据库的内容重新写回 Redis
                stringRedisTemplate.opsForZSet().add(key,followUserId.toString(),createTime.getTime());
                return true;
            }
            // 数据库数据也为空，证明该用户没有关注，返回 false
            return false;
        }
        return isFollow;
    }
}
