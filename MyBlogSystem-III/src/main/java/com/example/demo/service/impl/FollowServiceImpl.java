package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Follow;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.FollowMapper;
import com.example.demo.service.ICommentService;
import com.example.demo.service.IFollowService;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements IFollowService {
}
