package com.example.demo.controller;

import com.example.demo.utils.AjaxResult;
import com.example.demo.utils.SecurityUnit;
import com.example.demo.utils.SessionUnit;
import com.example.demo.entity.User;
import com.example.demo.service.impl.MailRedisServiceImpl;
import com.example.demo.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailServiceImpl service;
    @Autowired
    private MailRedisServiceImpl redisService;


    @RequestMapping("/send")
    public Object sendCode(String mail) {
        if(!service.isMail(mail)) {
            return AjaxResult.fail(-1,"当前输入邮件格式不符合要求");
        }
        // 发送验证码给该邮箱
        service.sendCode(mail);
        return AjaxResult.success(1,"发送验证码成功");
    }

    @RequestMapping("/bind")
    public Object bindMail(HttpServletRequest request,String code) {
        if(!service.bindMail(code)) {
            return AjaxResult.fail(-2,"验证码错误或者已经过期");
        }
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        String mail = redisService.getMail(); // 获取到当前待验证邮箱
        if(mail == null) {
            return AjaxResult.fail(-2,"待验证邮箱已经过期");
        }
        int res = service.changeEmail(curUser.getId(), mail);
        if(res != 1) {
            AjaxResult.fail(-1,"数据库更新失败");
        }
        return AjaxResult.success(res,"绑定邮箱成功");
    }

    @RequestMapping("/retrieve-send")
    public Object retrieveSend(String username) {
        if(!StringUtils.hasLength(username)) {
            return AjaxResult.fail(-1,"用户名为空");
        }
        // 根据用户名获取邮箱
        String mail = service.retrieveSend(username);
        if(mail == null) {
            return AjaxResult.fail(-2,"该用户没有绑定邮箱");
        }
        // 发送给邮箱验证码
        service.sendCode(mail);
        // 将用户名存储的 Redis 中,过期时间为 3 分钟
        redisService.saveUsername(username);
        return AjaxResult.success(1,"发送验证码成功");
    }

    @RequestMapping("/retrieve")
    public Object retrievePassword(String newPassword, String code) {
        String username = redisService.getUsername(); // 从 Redis 中获取用户名
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(newPassword)) {
            return AjaxResult.fail(-1,"用户名或者新密码为空");
        }
        // 检查输入验证码是否正确
        String redisCode = redisService.getCode();
        if(redisCode == null) {
            return AjaxResult.fail(-2,"该验证码已经过期");
        }
        if(!redisCode.equals(code)) {
            return AjaxResult.fail(-3,"验证码不正确");
        }
        String password = SecurityUnit.encrypt(newPassword); // 将密码进行加密后再加入数据库
        // 如果验证码正确,则根据用户名修改该用户的密码
        int res = service.retrievePassword(username,password);
        if(res != 1) {
            return AjaxResult.fail(-1,"数据库修改密码失败");
        }
        return AjaxResult.success(res,"修改密码成功");
    }
}
