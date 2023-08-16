package com.example.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Date create_time;
    private Date update_time;
    private Integer user_id;
    private int likes;
    private int views;
}
