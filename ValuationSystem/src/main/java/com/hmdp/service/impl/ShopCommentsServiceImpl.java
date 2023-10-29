package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.ShopComments;
import com.hmdp.mapper.ShopCommentsMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.service.IShopCommentsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ShopCommentsServiceImpl extends ServiceImpl<ShopCommentsMapper, ShopComments> implements IShopCommentsService {
    @Resource
    IShopCommentsService shopCommentsService;
    public Result commentShop(Long userId, Long shopId, String content, Integer stars) {
       ShopComments shopComments = new ShopComments();
       shopComments.setUserId(userId);
       shopComments.setShopId(shopId);
       shopComments.setContent(content);
       shopComments.setStars(stars);
       boolean ret = shopCommentsService.save(shopComments);
       if(ret) {
           Result.ok(true);
       } 
       return Result.fail("评论插入数据库失败");
    }

    @Override
    public Result showShopComments(Long shopId) {
        List<ShopComments> ret = shopCommentsService.query().eq("shopId",shopId).list();
        return Result.ok(ret);
    }
}
