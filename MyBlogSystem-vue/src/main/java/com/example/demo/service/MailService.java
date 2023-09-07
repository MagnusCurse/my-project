package com.example.demo.service;

import com.example.demo.common.AjaxResult;
import com.example.demo.mapper.MailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class MailService {
    @Autowired
    private MailMapper mapper;

    @Autowired
    private MailRedisService redisService;

    @Autowired
    private JavaMailSender sender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    /**
     * 判断邮箱格式是否正确
     * @param mail
     * @return
     */
    public boolean isMail(String mail) {
        if(mail == null || mail.length() < 1 || mail.length() > 256) {
            return false;
        }
        // 这行代码创建了一个正则表达式模式对象，用于匹配邮箱地址的正则表达式。
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        // 这行代码使用创建的正则表达式模式来检查传入的邮箱字符串是否与正则表达式匹配
        return pattern.matcher(mail).matches();
    }

    /**
     * 生成随机的六位数验证码
     * @return
     */
    public String createCode() {
        String numbers = "0123456789";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < 6; i++) {
            int index = random.nextInt(numbers.length());
            char c = numbers.charAt(index);
            code.append(c);
        }
        return code.toString();
    }

    /**
     * 发送验证码给该邮箱
     * @param receive
     */
    public void sendCode(String receive) {
        MimeMailMessage mimeMailMessage = new MimeMailMessage(sender.createMimeMessage());
        // 邮件发送人
        mimeMailMessage.setFrom(sendMailer);
        // 邮件接收人
        mimeMailMessage.setTo(receive);
        // 邮件主题
        mimeMailMessage.setSubject("Verification Code");
        String code = createCode(); // 随机生成六位数验证码
        // 邮件内容
        mimeMailMessage.setText("The verification code is" + code);
        // 将验证码存储到 Redis 中
        redisService.saveCode(code);
        // 将待验证邮箱存储到 Redis 中
        redisService.saveMail(receive);
        // 邮件发送时间
        mimeMailMessage.setSentDate(new Date());
        // 发送邮件
        sender.send(mimeMailMessage.getMimeMessage());
    }

    /**
     * 绑定该邮箱
     * @param code
     * @return
     */
    public boolean bindMail(String code) {
        String redisCode = redisService.getCode();
        // 说明当前验证码已经过期
        if(redisCode == null) {
            return false;
        }
        return redisCode.equals(code);
    }

    /**
     * 修改邮箱
     * @param email
     * @return
     */
    public int changeEmail(Integer id, String email){
        return mapper.changeEmail(id, email);
    }

    /**
     * 根据绑定的邮箱找回密码
     * @param username
     * @param password
     * @return
     */
    public int retrievePassword(String username, String password) {
        return mapper.retrievePassword(username,password);
    }

    /**
     * 发送验证码给对应用户名的邮箱
     * @param username
     * @return
     */
    public String retrieveSend(String username) {
        return mapper.retrieveSend(username);
    }
}
