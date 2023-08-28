package com.example.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private Integer id;
    private Integer parent_id;
    private Integer user_id;
    private Integer blog_id;
    private String username;
    private String comment;
    private Date create_time;
    private int like_count;
    private Integer replied_id;
}
