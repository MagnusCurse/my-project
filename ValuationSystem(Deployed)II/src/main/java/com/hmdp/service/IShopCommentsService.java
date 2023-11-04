package com.hmdp.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopComments;


public interface IShopCommentsService extends IService<ShopComments> {
    public Result commentShop(Long userId, Long shopId, String content, Integer stars);

    public Result showShopComments(Long shopId);
}
