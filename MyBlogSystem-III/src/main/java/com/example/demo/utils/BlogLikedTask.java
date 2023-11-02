package com.example.demo.utils;

import com.example.demo.service.impl.BlogLikedService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class BlogLikedTask extends QuartzJobBean {
    @Autowired
    private BlogLikedService service;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("LikeTask---------", sdf.format(new Date()));

        // 将 Redis 里的点赞数据同步到数据库
        service.saveLikeFromRedisToDb();
        // 将 Redis 里面点赞数量同步到数据库
        service.saveLikeCountFromRedisToDb();
    }
}
