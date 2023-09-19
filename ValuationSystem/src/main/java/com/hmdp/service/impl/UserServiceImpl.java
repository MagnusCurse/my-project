package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RedisData;
import com.hmdp.utils.RegexUtils;
import com.hmdp.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone, HttpSession session) {
       // TODO 校验手机号码
       if(RegexUtils.isPhoneInvalid(phone)) {
           return Result.fail("手机号码格式错误!!");
       }
       // TODO 生成随机验证码
       String code = RandomUtil.randomNumbers(6);
       System.out.println("Verification Code:" + code);
       // TODO 将验证码存入到 Redis 中
       // 因为 Redis 间的空间是共享的, 这里用手机号组合作为 key
       // 设置过期时间为 2 分钟
       stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY + phone, code,
               RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
       // TODO 发送验证码给手机 : 这里后面再来实现

       return Result.ok("发送验证码成功");
    }

    @Override
    public Result phoneLogin(LoginFormDTO loginForm, HttpSession session) {
        System.out.println("phoneLogin 被调用了");
        // TODO 校验手机号
        String phone = loginForm.getPhone();
        if(RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号码格式错误");
        }
        // TODO 校验验证码
        Object cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if(cacheCode == null || !cacheCode.toString().equals(code)) {
            return Result.fail("验证码错误");
        }
        // TODO 根据手机号查询用户 : select * from tb_user where phone = ?
        User user = query().eq("phone",phone).one();

        // TODO 判断用户是否存在
        if(user == null) {
            // 用户不存在, 创建一个新用户, 这里相当于注册
            user = createNewUser(phone);
        }
        // TODO 将用户信息保存到 Redis 中,TODO 随机生成 token, 作为登录令牌
        String token = UUID.randomUUID().toString();
        // 将 User 对象转换为 HashMap 进行存储
        // BeanUtil.copyProperties(user, UserDTO.class) : 将 User 对象转换为一个 UserDTO 的对象
        UserDTO dto = BeanUtil.copyProperties(user, UserDTO.class);
        // 将 UserDto 转换为哈希表, 属性字段作为 key, 属性值作为 value
        // 将字段的所有值都转换为 String 才能存入 StringRedisTemplate 中
        Map<String, Object> userMap = BeanUtil.beanToMap(dto, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // TODO 将 token 存储到 Redis 中, 值为用户信息的哈希表
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置 token 的有效期为 30 分钟
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 将 token 返回给前端
        return Result.ok(token);
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
