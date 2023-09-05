package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.User;
import com.example.demo.service.MailRedisService;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService service;
    @Autowired
    private MailRedisService redisService;


    @RequestMapping("/send")
    public Object sendCode(HttpServletRequest request,String mail) {
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
            return AjaxResult.fail(-1,"验证码错误或者已经过期");
        }
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        String mail = redisService.getMail(); // 获取到当前待验证邮箱
        if(mail == null) {
            return AjaxResult.fail(-1,"待验证邮箱已经过期");
        }
        int res = service.changeEmail(curUser.getId(), mail);
        if(res != 1) {
            AjaxResult.fail(-1,"数据库更新失败");
        }
        return AjaxResult.success(res,"绑定邮箱成功");
    }
}
