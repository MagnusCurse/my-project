package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.EmailUnit;
import com.example.demo.common.SecurityUnit;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/retrieve")
public class RetrieveController {
    @Autowired
    EmailUnit emailUnit;
    @Autowired
    UserService userService;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 给邮件发送六位数验证码
     * @param username
     * @return
     */
    @RequestMapping("/code")
    public Object sendCode(String username) {
        String email = userService.selectEmail(username);
        if (email != null && emailUnit.isEmail(email)) {
            StringBuffer code = emailUnit.CreateCode();// 生成六位数验证码
            stringRedisTemplate.opsForValue().set(email, code.toString());// 将该验证码存入Redis中
            stringRedisTemplate.expire(email, 15, TimeUnit.MINUTES);// 设置验证码过期时间为三分钟
            // 发送邮件到指定邮箱
            emailUnit.sendEmail(email, code.toString(),username);
            return AjaxResult.success(200, "验证码发送成功,有效期为三分钟");
        } else {
            return AjaxResult.fail(-1, "该用户没有绑定该邮箱");
        }
    }

    /**
     * 重置密码功能
     * @param username
     * @param password
     * @param repassword
     * @param code
     * @return
     */
    @RequestMapping("/resetpassword")
    public Object savePassword(String username,String password, String repassword, String code) {
        String email = userService.selectEmail(username);// 通过用户名获取到邮箱
        if(emailUnit.checkPassword(password,repassword)){// 检查两次密码输入是否一致
            String getCode = stringRedisTemplate.opsForValue().get(email);// 得到Redis中存储的code
            if(getCode.equals(code)){// 判断两个验证码是否相等
                String encryptPassword = SecurityUnit.encrypt(password);// 对密码进行加密
                return userService.resetPassword(username,encryptPassword);// 修改成功,返回修改的行数
            }
        }
        return 0;// 修改失败,返回 0
    }
}
