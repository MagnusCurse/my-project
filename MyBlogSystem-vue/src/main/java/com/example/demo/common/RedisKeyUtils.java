package com.example.demo.common;

public class RedisKeyUtils {
    // 保存用户点赞数据的 key
    public static final String MAP_KEY_USER_LIKED = "MAP_USER_LIKED";
    // 保存博客被点赞数量的 key
    public static final String MAP_KEY_BLOG_LIKED_COUNT = "MAP_BLOG_LIKED_COUNT";

    /**
     *
     * @param likedBlogId 被点赞博客的 id
     * @param likedPostId 点赞该博客用户的 id
     * @return
     */
    public static String getLikedKey(String likedBlogId,String likedPostId){
        StringBuilder builder = new StringBuilder();
        builder.append(likedBlogId);
        builder.append("::");
        builder.append(likedPostId);
        return builder.toString();
    }
}
