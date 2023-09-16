package com.hmdp.utils;

import com.hmdp.dto.UserDTO;

public class UserHolder {
    // ThreadLocal 是 Java 中的一个类，用于创建线程本地变量。
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
