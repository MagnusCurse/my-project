package com.example.demo.common;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 登录安全工具类
 */
@Component
public class SecurityUtils {
    /**
     * 对密码进行加盐加密
     * @param password
     * @return
     */
    public String encrypt(String password){
        String salt = UUID.randomUUID().toString().replace("-",""); // 获取32位盐值
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes()); // 对盐值加密码进行MD5加密
        return salt + finalPassword; // 返回盐值加上最终加密后的密码就是加密密码
    }

    /**
     * 解密并且验证密码是否正确
     * @param password 待验证的密码
     * @param finalPassword 数据库中的经过加密的密码
     * @return
     */
    public boolean decrypt(String password,String finalPassword){
        String salt = finalPassword.substring(0,32); // 获取前 32 位盐值
        String securityPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        return finalPassword.equals(salt + securityPassword);
    }
}
