package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.dto.LoginFormDTO;
import com.hmdp.dto.Result;
import com.hmdp.dto.UserDTO;
import com.hmdp.entity.User;
import com.hmdp.mapper.UserMapper;
import com.hmdp.service.IUserService;
import com.hmdp.utils.*;
import org.aspectj.weaver.AjAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
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

    // 用于创建新用户
    private User createNewUser(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(SystemConstants.USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 将该用户信息保存到数据库中
        save(user);
        return user;
    }

    @Override
    public Result sendCode(String phone, HttpSession session) {
       //  校验手机号码
       if(RegexUtils.isPhoneInvalid(phone)) {
           return Result.fail("手机号码格式错误!!");
       }
       //  生成随机验证码
       String code = RandomUtil.randomNumbers(6);
       System.out.println("Verification Code:" + code);
       //  将验证码存入到 Redis 中
       // 因为 Redis 间的空间是共享的, 这里用手机号组合作为 key
       // 设置过期时间为 2 分钟
       stringRedisTemplate.opsForValue().set(RedisConstants.LOGIN_CODE_KEY + phone, code,
               RedisConstants.LOGIN_CODE_TTL, TimeUnit.MINUTES);
       //  发送验证码给手机 : 这里后面再来实现
       return Result.ok(code); // 这里先把验证码发送给前端, 直接使用即可
    }

    @Override
    public Result logout(HttpServletRequest request) {
        //  从请求头中获取到 token
        String token = request.getHeader(SystemConstants.USER_LOGIN_TOKEN);
        if(StrUtil.isBlank(token)) {
            return Result.ok("当前用户未登录");
        }
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        //  将该用户记录从 Redis 中移除
        stringRedisTemplate.opsForHash().delete(tokenKey);
        //  将用户对象从 ThreadLocal 中移除
        UserHolder.removeUser();
        return Result.ok("退出登录成功");
    }

    @Override
    public Result phoneLogin(LoginFormDTO loginForm, HttpSession session) {
        // 校验手机号
        String phone = loginForm.getPhone();
        if(RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号码格式错误");
        }
        // 校验验证码
        Object cacheCode = stringRedisTemplate.opsForValue().get(RedisConstants.LOGIN_CODE_KEY + phone);
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
        // 将用户信息保存到 Redis 中, 随机生成 token, 作为登录令牌
        String token = UUID.randomUUID().toString();
        // 将 User 对象转换为 HashMap 进行存储
        // BeanUtil.copyProperties(user, UserDTO.class) : 将 User 对象转换为一个 UserDTO 的对象
        UserDTO dto = BeanUtil.copyProperties(user, UserDTO.class);
        // 将 UserDto 转换为哈希表, 属性字段作为 key, 属性值作为 value
        // 将字段的所有值都转换为 String 才能存入 StringRedisTemplate 中
        // Map 的 key 是 UserDTO 的字段，而 value 则是 UserDTO 的值
        Map<String, Object> userMap = BeanUtil.beanToMap(dto, new HashMap<>(),
                CopyOptions.create().setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // 将 token 存储到 Redis 中, 值为用户信息的哈希表
        String tokenKey = RedisConstants.LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 设置 token 的有效期为 30 分钟
        stringRedisTemplate.expire(tokenKey, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        // 将 token 返回给前端
        return Result.ok(token);
    }

    @Override
    public Result sign() {
        System.out.println("Using sign");
        //  获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        //  获取当前日期
        LocalDateTime now = LocalDateTime.now();
        // 拼接 key : 年/月
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = RedisConstants.USER_SIGN_KEY + userId + keySuffix;
        // 获取当天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //  写入 Redis 的 BitMap 中, true 表示为 1
        stringRedisTemplate.opsForValue().setBit(key,dayOfMonth - 1, true);
        return Result.ok();
    }

    @Override
    public Result signCount() {
        //  获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        //  获取当前日期
        LocalDateTime now = LocalDateTime.now();
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = RedisConstants.USER_SIGN_KEY + userId + keySuffix;
        // 获取当天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        //  获取本月截止到今天的所有签到记录, 返回一个十进制的数字 / 即统计从该月开始到今天连续签到的次数
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        if(result == null || result.isEmpty()) {
           // 没有签到记录
           return Result.ok(0);
        }
        // 获取到该十进制数字
        Long num = result.get(0);
        if(num == null || num == 0) {
            // 没有签到记录
            return  Result.ok(0);
        }
        //  循环遍历记录签到次数
        int count = 0; // 统计签到次数
        while(true) {
            // 让这个数字与 1 做与运算, 得到数字的最后一个 bit 位
            // 判断这个 bit 位是否为 0
            if((num & 1) == 0) {
                // 如果为 0, 说明未签到, 结束循环
                break;
            } else {
                count++;
            }
            // 把数字右移一位
            // >> 表示有符号右移, >>> 表示无符号右移动
            num >>>= 1;
        }
        return Result.ok(count);
    }
}
