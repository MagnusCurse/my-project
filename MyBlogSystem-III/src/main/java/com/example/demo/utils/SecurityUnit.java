package com.example.demo.utils;


import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 用于对密码安全性进行操作
 */
public class SecurityUnit {

    /**
     * 对密码进行加密
     * @param password
     * @return
     */
    public static String encrypt(String password){
        // 随机生成不同的 32 位的盐值
        String salt = UUID.randomUUID().toString().replace("-","");//替换“-”为空
        // 生成最终密码 (盐值 + 密码) 进行加密
        String finalPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        return salt + finalPassword;
    }

    /**
     * 解密验证密码正确性
     * @param password 待验证的密码
     * @param finalPassword 数据库中饭加密了的密码
     * @return
     */
    public static boolean decrypt(String password,String finalPassword){
        // 获得前面 32 位的盐值
        String salt = finalPassword.substring(0,32);
        // 通过待验证密码 + 盐值生成最终待确认密码
        String securityPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        return finalPassword.equals(salt + securityPassword);
    }
}
