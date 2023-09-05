package com.example.demo.controller;

import com.example.demo.common.AjaxResult;
import com.example.demo.common.SessionUnit;
import com.example.demo.model.User;
import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @RequestMapping("/bind")
    public Object bindEmail(HttpServletRequest request,String mail) {
        User curUser = SessionUnit.getLoginUser(request);
        if(curUser == null) {
            return AjaxResult.fail(-1,"当前用户对象为空");
        }
        return 0;
    }

    @RequestMapping("/send")
    public void sendCode(String text, String receive) {
        MimeMailMessage mimeMailMessage = new MimeMailMessage(sender.createMimeMessage());
        // 邮件发送人
        mimeMailMessage.setFrom(sendMailer);
        // 邮件接收人
        mimeMailMessage.setTo(receive);
        // 邮件主题
        mimeMailMessage.setSubject("Verification Code");
        // 邮件内容
        mimeMailMessage.setText(text);
        // 邮件发送时间
        mimeMailMessage.setSentDate(new Date());
        // 发送邮件
        sender.send(mimeMailMessage.getMimeMessage());
        System.out.println("发送邮件成功");
    }

}
