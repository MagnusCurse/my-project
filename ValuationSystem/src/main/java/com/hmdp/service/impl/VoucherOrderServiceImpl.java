package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.User;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisWorker;
import com.hmdp.utils.UserHolder;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;


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
    // NOTE 创建并初始化秒杀功能 lua 脚本
    private static final DefaultRedisScript<Long> SECKILL_SCRIPT;
    // 用静态代码块来初始化 DefaultRedisScript<Long>
    static {
        SECKILL_SCRIPT = new DefaultRedisScript<>();
        SECKILL_SCRIPT.setLocation(new ClassPathResource("SECKILL.lua"));
        SECKILL_SCRIPT.setResultType(Long.class);
    }

    /**
     * 采用乐观锁解决了超卖问题: stock 作为版本号
     * @param voucherId
     * @return
     */
    @Override
    public Result seckillVoucher(Long voucherId) {
        // TODO 获取当前用户 id
        Long userId = UserHolder.getUser().getId();
        // TODO 执行 lua 脚本
        Long result = stringRedisTemplate.execute(
               SECKILL_SCRIPT,
               Collections.emptyList(), // 这里传入一个空的列表,不要传 null
               voucherId.toString(),userId.toString()
        );
        // TODO 判断结果是否为 0
        int res = result.intValue();
        if(res != 0) {
           // 不为 0,为 1 是库存不足, 否则是用户已经下单
           return Result.fail(res == 1 ? "库存不足" : "不能重复下单");
        }
        // TODO 为 0, 将下单信息保存到阻塞队列
        long orderId = redisWorker.generateId("order"); // 生成订单 id
        // TODO 将下单信息保存到阻塞队列


        // TODO 返回订单 id
        return Result.ok(orderId);
    }


//    @Override
//    public Result seckillVoucher(Long voucherId) {
//        // TODO 查询优惠券信息
//        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
//        // TODO 判断秒杀是否开始
//        if(voucher.getBeginTime().isAfter(LocalDateTime.now())) {
//            // 秒杀还没有开始
//            return Result.fail("秒杀还未开始");
//        }
//        // TODO 判断秒杀是否已经结束
//        if(voucher.getEndTime().isBefore(LocalDateTime.now())) {
//            // 秒杀已经结束
//            return Result.fail("秒杀已经结束");
//        }
//        // TODO 判断库存是否足够用
//        if(voucher.getStock() < 1) {
//            return Result.fail("当前优惠券库存不足");
//        }
//        Long userId = UserHolder.getUser().getId(); // 获取当前用户 id
//
//        // TODO 创建锁对象
//        // SimpleRedisLock redisLock = new SimpleRedisLock("order:" + userId,stringRedisTemplate);
//        // TODO 这里改用 Redisson 创建锁对象
//        RLock redisLock = redissonClient.getLock("lock:order:" + userId);
//        // TODO 尝试获取锁 ( 没有参数表示如果获取不到直接失败 )
//        boolean lock = redisLock.tryLock();
//        if(!lock) { // 获取锁失败, 直接返回错误信息
//            return Result.fail("不允许重复抢购优惠券");
//        }
//
//        // ERR This locking method does not work in distributed or clustered environment
//        /*
//        // TODO 给每个用户 ID 进行加锁, 只有相同用户才会阻塞
//        // NOTE intern() 保证是安装字符串的值来进行加锁的, 去字符串线程池查找有没有相同值的字符串
//        synchronized (userId.toString().intern()) {
//            // NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
//            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
//            // TODO 返回订单 id
//            return proxy.createVoucherOrder(voucherId);
//        }
//        */
//        try {
//            // NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
//            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
//            // TODO 返回订单 id
//            return proxy.createVoucherOrder(voucherId);
//        } finally {
//            // TODO 释放锁
//            redisLock.unlock();
//        }
//    }


    //NOTE 创建订单
    @Transactional
    public Result createVoucherOrder(Long voucherId) {
        // TODO 实现一人一单功能, 即每个用户只能抢购一次
        Long userId = UserHolder.getUser().getId(); // 获取当前用户 id
        int count = query().eq("voucher_id",voucherId).eq("user_id",userId).count();
        if(count >= 1) {
            return Result.fail("当前用户已经抢购过了"); // 当前用户已经购买过了
        }

        // TODO 扣减库存
        boolean success = seckillVoucherService.update()
                .setSql("stock = stock - 1")
                .eq("voucher_id", voucherId)
                // NOTE 这里用 stock 当作版本号, 相当于实现了乐观锁, 只需要库存大于 0 即可以修改
                .gt("stock",0)
                .update();
        if(!success) {
            return Result.fail("更新库存失败");
        }


        // TODO 创建优惠券订单
        VoucherOrder voucherOrder = new VoucherOrder();
        // 使用 Redis 生成订单 id
        long orderId = redisWorker.generateId("order");
        voucherOrder.setId(orderId);
        // 获取用户 id
        voucherOrder.setUserId(userId);
        // 代金券 id
        voucherOrder.setVoucherId(voucherId);
        // 将代金券订单保存到数据库中
        save(voucherOrder);
        // TODO 返回订单 id
        return Result.ok(orderId);
    }
}
