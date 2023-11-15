package com.example.demo.model;

import lombok.Data;

import java.util.Date;

@Data
public class Articleinfo {
    private int id;
    private String title;
    private String content;
    private String createtime;
    private String updatetime;
    private int uid;
    private int rcount;
    private int state;
    private int likes;
    private int views;
}
