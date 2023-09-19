package com.hmdp.service.impl;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.CacheClient;
import com.hmdp.utils.RedisConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    CacheClient cacheClient;

    @Override
    public Result queryById(Long id) {
        // TODO 通过缓存工具类获取 shop
        Shop shop = cacheClient.queryById(RedisConstants.CACHE_SHOP_KEY,id,Shop.class,this::getById,RedisConstants.CACHE_SHOP_TTL,TimeUnit.MINUTES);
        // TODO 返回商铺数据给前端
        return Result.ok(shop);
    }



    // 这个操作一般不由用户来操作, 而是管理层进行更新
    @Transactional // 将其操作变成一个事务
    @Override
    public Result updateShopRedis(Shop shop) {
        // TODO 先更新数据库
        Long id = shop.getId();
        if(id == null) {
            return Result.fail("商铺 id 不能为空");
        }
        // 根据 id 更新数据库
        updateById(shop);
        // TODO 再删除缓存
        stringRedisTemplate.delete(RedisConstants.CACHE_SHOP_KEY + id);
        return Result.ok();
    }
}
