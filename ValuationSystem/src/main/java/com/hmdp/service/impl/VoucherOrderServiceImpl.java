package com.hmdp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisWorker;
import com.hmdp.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisWorker redisWorker;
    @Resource
    RedissonClient redissonClient;

    // NOTE 代理对象
    private IVoucherOrderService proxy;

    // ERR 创建阻塞队列，后面改为使用 Redis 的 Stream 消息队列
    /* private static final BlockingQueue<VoucherOrder> orderBlockingQueue = new ArrayBlockingQueue<>(1024 * 1024); */

    // NOTE 创建线程池
    private static final ExecutorService seckillOrderExecutor = Executors.newSingleThreadExecutor();

    // NOTE 当类初始化完毕,通过线程池执行线程
    @PostConstruct // 在当前类初始化完毕后开始执行 init 方法
    private void init() {
        seckillOrderExecutor.submit(new VoucherOrderHandler());
    }
    private class VoucherOrderHandler implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    // TODO 获取消息队列中的消息 XREADGROUP GROUP g1 c1 COUNT 1 BLOCK 2000 STRAMS stream.orders >
                    List<MapRecord<String,Object,Object>> list = stringRedisTemplate.opsForStream().read(
                            Consumer.from("g1","c1"), // 组名和消费者名
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(2)), // 读取消息个数和阻塞等待时间
                            StreamOffset.create("stream.orders", ReadOffset.lastConsumed()) //
                    );
                    // TODO 判断消息是否获取成功
                    if(list == null || list.isEmpty()) {
                        // 获取失败, 说明没有消息, 则继续循环
                        continue;
                    }
                    // TODO 解析消息中的订单信息
                    MapRecord<String,Object,Object> record = list.get(0);
                    Map<Object,Object> values = record.getValue();
                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(values, new VoucherOrder(), true);
                    // TODO 获取成功, 下单
                    handleVoucherOrder(voucherOrder);
                    // TODO ACK 确认 SACK stream.orders g1 id
                    stringRedisTemplate.opsForStream().acknowledge("stream.orders", "g1", record.getId());
                }
            }catch (Exception e) {
                log.error("消息队列读取出现异常:" + e);
                // TODO 处理 PendingList 队列
                handlePendingList();
            }
        }
    }

    /**
     * 处理消息队列的 PendingList 队列
     */
    private void handlePendingList() {
        try {
            while (true) {
                // TODO 获取消息队列中的消息 XREADGROUP GROUP g1 c1 COUNT 1 STRAMS stream.orders 0
                List<MapRecord<String,Object,Object>> list = stringRedisTemplate.opsForStream().read(
                        Consumer.from("g1","c1"), // 组名和消费者名
                        StreamReadOptions.empty().count(1), // 读取消息个数和阻塞等待时间
                        StreamOffset.create("stream.orders", ReadOffset.from("0")) //
                );
                // TODO 判断消息是否获取成功
                if(list == null || list.isEmpty()) {
                    // 获取失败, 说明没有消息, 则结束循环
                    break;
                }
                // TODO 解析消息中的订单信息
                MapRecord<String,Object,Object> record = list.get(0);
                Map<Object,Object> values = record.getValue();
                VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(values, new VoucherOrder(), true);
                // TODO 获取成功, 下单
                handleVoucherOrder(voucherOrder);
                // TODO ACK 确认 SACK stream.orders g1 id
                stringRedisTemplate.opsForStream().acknowledge("stream.orders", "g1", record.getId());
            }
        }catch (Exception e) {
            log.error("消息队列 PendingList 读取出现异常:" + e);
        }
    }

    // NOTE 创建并初始化秒杀功能 lua 脚本
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    // 用静态代码块来初始化 DefaultRedisScript<Long>
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("lua/seckill.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    /**
     * 用来处理订单
     * @param voucherOrder
     */
    private void handleVoucherOrder(VoucherOrder voucherOrder) {
        System.out.println("调用了 handleVoucherOrder");

        // ERR Can't use this way to get the userId, because there is not the main thread but a child thread
        /* Long userId = UserHolder.getUser().getId(); // 获取当前用户 id */
        Long userId = voucherOrder.getUserId();

        // TODO 创建锁对象
        // SimpleRedisLock redisLock = new SimpleRedisLock("order:" + userId,stringRedisTemplate);
        // TODO 这里改用 Redisson 创建锁对象
        RLock redisLock = redissonClient.getLock("lock:order:" + userId);
        // TODO 尝试获取锁 ( 没有参数表示如果获取不到直接失败 )
        boolean lock = redisLock.tryLock();
        if(!lock) { // 获取锁失败, 直接返回错误信息
           log.error("不允许重复下单");
        }
        // ERR This locking method does not work in distributed or clustered environment
        /*
        // TODO 给每个用户 ID 进行加锁, 只有相同用户才会阻塞
        // NOTE intern() 保证是安装字符串的值来进行加锁的, 去字符串线程池查找有没有相同值的字符串
        synchronized (userId.toString().intern()) {
            // NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            // TODO 返回订单 id
            return proxy.createVoucherOrder(voucherId);
        }
        */

        try {
            // ERR Can't get proxy here like this, because now is a child thread but not a main thread,so init it in seckillVoucher(Long voucherId) method
            /*
             NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
             IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy(); */
             proxy.createVoucherOrder(voucherOrder); // 通过代理对象调用创建订单方法
        } finally {
            // TODO 释放锁
            redisLock.unlock();
        }
    }

    /**
     * 用来创建订单对象
     * @param voucherOrder
     */
    @Transactional
    public void createVoucherOrder(VoucherOrder voucherOrder) {
        // TODO 实现一人一单功能, 即每个用户只能抢购一次
        Long userId = voucherOrder.getUserId(); // 这里是子线程,不能用 UserHolder 获取
        int count = query().eq("voucher_id",voucherOrder.getVoucherId()).eq("user_id",userId).count();
        if(count >= 1) {
            log.error("当前用户已经抢购了");
            return;
        }

        // TODO 扣减库存
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherOrder.getVoucherId())
                // NOTE 这里用 stock 当作版本号, 相当于实现了乐观锁, 只需要库存大于 0 即可以修改
                .gt("stock",0)
                .update();

        if(!success) {
            log.error("库存不足");
            return;
        }
        save(voucherOrder);
    }

    /**
     * 秒杀优惠券
     */
    @Override
    public Result seckillVoucher(Long voucherId) {
        // TODO 获取当前用户 id
        Long userId = UserHolder.getUser().getId();
        // TODO 获取订单 id
        Long orderId = redisWorker.generateId("order");
        // TODO 执行 lua 脚本, 进行判断, 削减库存和将订单信息加入消息队列操作
        Long result = stringRedisTemplate.execute(
                SECKILL_SCRIPT,
                Collections.emptyList(), // 这里传入一个空的列表,不要传 null
                voucherId.toString(),userId.toString(),orderId.toString() // 传入三个参数
        );
        // TODO 判断结果是否为 0
        int res = result.intValue();
        if(res != 0) {
            // 不为 0,为 1 是库存不足, 否则是用户已经下单
            return Result.fail(String.valueOf(res));
        }
        // ERR 这里不使用阻塞队列, 改用 Stream 消息队列
        /*
        // TODO 为 0, 将下单信息保存到阻塞队列
        // TODO 将下单信息保存到阻塞队列, 创建另一个线程对订单的创建进行异步处理,提高效率
        // TODO 创建优惠券订单
        // 创建订单对象
        VoucherOrder voucherOrder = new VoucherOrder();
        // 设置订单 id
        voucherOrder.setId(orderId);
        // 设置用户 id
        voucherOrder.setUserId(userId);
        // 设置代金券 id
        voucherOrder.setVoucherId(voucherId);
        // 将订单加入到阻塞队列中
        orderBlockingQueue.add(voucherOrder);
        */

        // TODO 获取到代理对象
        proxy = (IVoucherOrderService) AopContext.currentProxy();
        // TODO 返回订单 id
        return Result.ok(orderId);
    }
}
