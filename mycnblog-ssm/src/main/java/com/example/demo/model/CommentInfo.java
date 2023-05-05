package com.example.demo.model;


import lombok.Data;

import java.util.Date;

@Data
public class CommentInfo {
    private Integer aid;
    private String content;
    private String createtime;
    private String username;
}
