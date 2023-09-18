package com.hmdp.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryById(Long id) {
        String key = RedisConstants.CACHE_SHOP_KEY + id;
        // TODO 从 Redis 中查询商铺缓存 / 这里也可以用哈希存储,这里采用字符串存储
        String shopJson = stringRedisTemplate.opsForValue().get(key);
        // TODO 判断缓存是否存在数据
        if(StrUtil.isNotBlank(shopJson)) {
            // 缓存存在, 直接返回即可
            Shop shop = JSONUtil.toBean(shopJson, Shop.class);
            return Result.ok(shop);
        }
        // TODO 缓存中不存在, 根据 id 查询数据库
        Shop shop = getById(id);
        if(shop == null) {
            return Result.fail("店铺不存在");
        }
        // TODO 存在将其写入 Redis, 转换为 JSON 格式
        stringRedisTemplate.opsForValue().set(key,JSONUtil.toJsonStr(shop));
        // TODO 返回商铺数据给前端
        return Result.ok(shop);
    }
}
