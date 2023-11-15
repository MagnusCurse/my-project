package com.example.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 一些与邮箱的相关功能
 */
@Component
public class EmailUnit {

    /**
     * 验证密码和重新输入的密码是否一致
     * @param password
     * @param repeatPassword
     * @return
     */
    public boolean checkPassword(String password,String repeatPassword){
        return password.equals(repeatPassword);
    }

    /**
     * 判断邮箱格式是否正确
     * @param email
     * @return false或者true
     */

    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * 邮件发送功能
     * @param from_email 发送人的邮箱
     * @param pwd 授权码
     * @param recevices  接收人的邮箱
     * @param code 验证码
     * @param name 收件人的姓名
     * @return
     */
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}") // 从application.yml配置文件中获取
    private String from_email; // 发送发邮箱地址

    public String sendEmail(String recevices, String code, String name) {
        SimpleMailMessage message = new SimpleMailMessage();//创建一个邮箱发送对象
        message.setFrom(from_email);
        message.setTo(recevices);
        message.setSubject("验证码");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body><p style='font-size: 20px;font-weight:bold;'>尊敬的："+name+"，您好！</p>"
                + "<p style='text-indent:2em; font-size: 20px;'>欢迎您本次的验证码是 "
                + "<span style='font-size:30px;font-weight:bold;color:red'>" + code + "</span>，3分钟之内有效，请尽快使用！</p>"
                + "<p style='text-align:right; padding-right: 20px;'"
                + "<a href='http://120.79.29.170' style='font-size: 18px'>Web有限公司</a></p>"
                + "<span style='font-size: 18px; float:right; margin-right: 60px;'>" + sdf.format(new Date()) + "</span></body></html>";
        message.setText(str);//设置邮件内容
        javaMailSender.send(message);//发送邮件
        return "发送验证码成功";
    }

    /**
     * 生成随机的六位数验证码
     * @return
     */
    public StringBuffer CreateCode() {
        String dates = "0123456789";
        StringBuffer code = new StringBuffer();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            int index = r.nextInt(dates.length());
            char c = dates.charAt(index);
            code.append(c);
        }
        return code;
    }
}
