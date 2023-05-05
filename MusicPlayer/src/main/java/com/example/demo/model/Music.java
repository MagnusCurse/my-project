package com.example.demo.model;

import lombok.Data;

@Data
public class Music {
    private int id;
    private String title;
    private String singer;
    private String uploadtime;
    private String url;
    private int user_id;
}
