package com.example.demo.common;


import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.regex.Pattern;

@Component
public class MailUtils {
    /**
     * 判断邮箱格式是否正确
     * @param mail
     * @return
     */
    public static boolean isMail(String mail) {
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
    public String CreateCode() {
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
}
