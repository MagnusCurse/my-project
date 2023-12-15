package com.example.demo.utils;

public class RedisKeyUtils {
    // 保存用户点赞数据的 key
    public static final String BLOG_LIKED_KEY = "blog:liked:";

    // 保存该博客用户浏览数量的 key
    public static final String BLOG_VIEWED_KEY = "blog:viewed:";
    // 防止恶意刷新浏览量的 key
    public static final String BLOG_VIEWED_LIMIT = "blog:viewed:limit:";

    // 记录关注记录的 key
    public static final String USER_FOLLOWED_KEY = "user:followed:";

    // 保存验证码的 key
    public static final String MAP_KEY_VERIFICATION_CODE = "map_verification_code";

    // 需要验证的邮箱的 key
    public static final String MAP_KEY_VERIFICATION_MAIL = "map_verification_mail";

    // 找回密码功能用来获取邮箱的用户名的 key
    public static final String MAP_KEY_USERNAME_MAIL = "map_key_username_mail";

}
