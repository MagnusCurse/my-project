package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserInfo implements Serializable{// 因为要存入 Redis 中,要让 UserInfo 可以序列化
    private int id;
    private String username;
    private String password;
    private String photo;
    private Date createtime;
    private Date updatetime;
    private int state;
    private String email;
    private String nickname;
    private String introduction;
}
