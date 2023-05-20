package com.example.demo.model;


import lombok.Data;

import java.util.Date;

@Data
public class CommentInfo {

    private Integer commentID;
    private Integer parentCommentID;
    private Integer userID;
    private String content;
    private String createtime;
    private Integer likes;
    private Integer articleID;

}
