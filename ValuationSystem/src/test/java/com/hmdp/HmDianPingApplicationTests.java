package com.hmdp;

import com.hmdp.entity.Shop;
import com.hmdp.service.IShopService;
import com.hmdp.service.impl.ShopServiceImpl;
import com.hmdp.utils.RedisConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public
class HmDianPingApplicationTests {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ShopServiceImpl shopService;

    @Test
    public void loadShopDataToRedis() {
        // TODO 从数据库中查询所有店铺信息
        List<Shop> list = shopService.list();
        // TODO 将店铺按照 typeId 分组分别存入一个集合
        Map<Long, List<Shop>> map = list.stream().collect(Collectors.groupingBy(Shop :: getTypeId));
        // TODO 分批将其写入 Redis
        for(Map.Entry<Long, List<Shop>> entry : map.entrySet()) {
            // 获取类型 id
            long typeId = entry.getKey();
            String key = RedisConstants.SHOP_GEO_KEY + typeId;
            // 获取该类型的店铺数据
            List<Shop> shops = entry.getValue();
            // NOTE 使用 Redis Geo 迭代器
            List<RedisGeoCommands.GeoLocation<String>> locations =
                    new ArrayList<>(shops.size());
            // 写入 Redis,将内容写入迭代器 : GEOADD Key 经度 纬度 member
            for (Shop shop : shops) {
               locations.add(new RedisGeoCommands.GeoLocation<>(
                       shop.getId().toString(),
                       new Point(shop.getX(), shop.getY())
               ));
            }
            // 将内容直接写入 Redis
            stringRedisTemplate.opsForGeo().add(key,locations);
        }
    }
}
