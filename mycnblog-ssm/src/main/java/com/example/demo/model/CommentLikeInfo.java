package com.example.demo.model;

import lombok.Data;

@Data
public class CommentLikeInfo {

    private int id;
    private int userID;
    private int commentID;
    private int articleID;

}
