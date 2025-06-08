package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
@TableName("comments")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer parentId;
    private Integer userId;
    private Integer blogId;
    private String username;
    private String comment;
    private Date createTime;
    private int likeCount;
    private String repliedUsername;
}
