package com.hmdp.service.impl;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hmdp.dto.Result;
import com.hmdp.entity.Shop;
import com.hmdp.mapper.ShopMapper;
import com.hmdp.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.CacheClient;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.SystemConstants;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.redis.connection.RedisCommands;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.*;
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

    @Override
    public Result queryShopByType(Integer typeId, Integer current, Double x, Double y) {
        // TODO 判断是否需要按照坐标进行查询
        if(x == null || y == null) {
           // 不涉及到坐标, 按照数据库进行查询
            Page<Shop> page = query()
                    .eq("type_id", typeId)
                    .page(new Page<>(current, SystemConstants.DEFAULT_PAGE_SIZE));
            return Result.ok(page.getRecords());
        }

        // TODO 计算分页参数
        int from = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        int end = current * SystemConstants.DEFAULT_PAGE_SIZE;

        // TODO 查询 Redis , 按照距离排序并分页。 结果: shopId,distance
        String key = RedisConstants.SHOP_GEO_KEY + typeId;
        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                stringRedisTemplate.opsForGeo()
                        .search(
                                key,
                                // 指定坐标点
                                GeoReference.fromCoordinate(x,y),
                                // 指定查找商铺的范围
                                new Distance(5000),
                                // limit 用于分页,但是只能指定 end, 所以后面还需要进行划分
                                RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs().includeDistance().limit(end)
                        );
        // 为空则返回空队列
        if(results == null) {
            return Result.ok(Collections.emptyList());
        }
        // TODO 将获取的结果转换成 list
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> list = results.getContent();
        // TODO 截取 from ~ end 的部分
        List<Long> ids = new ArrayList<>(list.size()); // 存储商铺 id
        Map<String,Distance> distanceMap =  new HashMap<>(list.size()); // 用于存储 shopId 和 距离
        if(list.size() <= from) {
            // 当前列表大小小于等于 from , 则没有下一页了, 直接结束即可
            return Result.ok(Collections.emptyList());
        }
        // 使用 stream 的 skip 来进行截取效率会更高
        list.stream().skip(from).forEach(result -> {
            // 获取到店铺的 id
            // 这里的 result 是一个 RedisGeoCommands.GeoLocation<String> 对象, 对象中有 shopId 和 distance
            String shopIdStr = result.getContent().getName();
            ids.add(Long.valueOf(shopIdStr));
            // 获取距离
            Distance distance = result.getDistance();
            distanceMap.put(shopIdStr, distance);
        });

        // TODO 根据 shopId 查询 shop, 这里要按顺序查询
        String idStr = StrUtil.join(",", ids);
        List<Shop> shops = query().in("id", ids)
                .last("ORDER BY FIELD(id," + idStr + ")").list();
        for(Shop shop : shops) {
            // 通过 shopId 从 distanceMap 中获取到 Distance
            shop.setDistance(distanceMap.get(shop.getId().toString()).getValue());
        }
        return Result.ok(shops);
    }
}
