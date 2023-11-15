package com.example.demo.model;

import lombok.Data;

@Data
public class DraftInfo {
    private int id;
    private String title;
    private String content;
    private String createtime;
    private String updatetime;
    private int uid;
}
