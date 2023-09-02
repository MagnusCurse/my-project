package com.example.demo.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private Integer id;
    private String username;
    private String password;
    private String photo;
    private Date create_time;
    private Date update_time;
    private String email;
    private String nickname;
    private String introduction;
    private String avatar_url;
}
