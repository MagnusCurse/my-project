package com.hmdp.controller;


import com.hmdp.dto.Result;
import com.hmdp.entity.ShopComments;
import com.hmdp.service.impl.ShopCommentsServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


@RestController
@RequestMapping("/shop-comments")
public class ShopCommentsController {
    @Resource
    private ShopCommentsServiceImpl shopCommentsService;
    @PostMapping("/comment")
    public Result commentShop(@RequestBody Map<String,String> body) {
       Long userId = Long.valueOf(body.get("userId"));
       Long shopId = Long.valueOf(body.get("shopId"));
       String content = body.get("content");
       Integer stars = Integer.valueOf(body.get("stars"));
       return shopCommentsService.commentShop(userId,shopId,content,stars);
    }

    @GetMapping("/show-shop-comments/{shopId}")
    public Result showShopComments(@PathVariable("shopId") Long shopId) {
        return shopCommentsService.showShopComments(shopId);
    }
}
