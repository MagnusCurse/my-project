package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("comment_info")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parent_id;
    @TableField("user_id")
    private Integer user_id;
    @TableField("blog_id")
    private Integer blog_id;
    private String username;
    private String comment;
    private Date create_time;
    private int like_count;
    private String replied_username;
}
