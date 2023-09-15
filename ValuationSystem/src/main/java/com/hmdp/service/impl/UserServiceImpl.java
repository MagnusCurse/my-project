package com.hmdp.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RedisData;
import com.hmdp.utils.RegexUtils;
import com.hmdp.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private StringRedisTemplate redisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
       // 校验手机号码
       if(RegexUtils.isPhoneInvalid(phone)) {
           return Result.fail("手机号码格式错误!!");
       }
       // 生成随机验证码
       String code = RandomUtil.randomNumbers(6);
       System.out.println("Verification Code:" + code);
       // 将验证码存入到 Redis 中
       // 设置过期时间为 2 分钟
       redisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY + phone, code, 2, TimeUnit.MINUTES);
       // 发送验证码给手机 : 这里后面再来实现

       return Result.ok("发送验证码成功");
    }

    @Override
    public Result phoneLogin(LoginFormDTO loginForm, HttpSession session) {
        // 校验手机号
        String phone = loginForm.getPhone();
        if(!RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号码格式错误");
        }
        // 校验验证码
        Object cacheCode = session.getAttribute("code");
        String code = loginForm.getCode();
        if(cacheCode == null || !cacheCode.toString().equals(code)) {
            return Result.fail("验证码错误");
        }
        // 根据手机号查询用户 : select * from tb_user where phone = ?
        User user = query().eq("phone",phone).one();

        // 判断用户是否存在
        if(user == null) {
            // 用户不存在, 创建一个新用户, 这里相当于注册
            user = createNewUser(phone);
        }
        // 将用户信息保存到 Redis 中
        session.setAttribute(RedisConstants.LOGIN_USER_KEY,user);
        return Result.ok("用户登录成功 / 创建新用户成功");
    }

    // 用于创建新用户
    private User createNewUser(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 将该用户信息保存到数据库中
        save(user);
        return user;
    }


}
