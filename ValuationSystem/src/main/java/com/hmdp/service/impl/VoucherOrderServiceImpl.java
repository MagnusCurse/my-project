package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisWorker;
import com.hmdp.utils.SimpleRedisLock;
import com.hmdp.utils.UserHolder;
import org.springframework.aop.framework.AopContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisWorker redisWorker;

    /**
     * 采用乐观锁解决了超卖问题: stock 作为版本号
     * @param voucherId
     * @return
     */

    @Override
    public Result seckillVoucher(Long voucherId) {
        // TODO 查询优惠券信息
        SeckillVoucher voucher = seckillVoucherService.getById(voucherId);
        // TODO 判断秒杀是否开始
        if(voucher.getBeginTime().isAfter(LocalDateTime.now())) {
            // 秒杀还没有开始
            return Result.fail("秒杀还未开始");
        }
        // TODO 判断秒杀是否已经结束
        if(voucher.getEndTime().isBefore(LocalDateTime.now())) {
            // 秒杀已经结束
            return Result.fail("秒杀已经结束");
        }
        // TODO 判断库存是否足够用
        if(voucher.getStock() < 1) {
            return Result.fail("当前优惠券库存不足");
        }
        Long userId = UserHolder.getUser().getId(); // 获取当前用户 id

        // TODO 创建锁对象
        SimpleRedisLock redisLock = new SimpleRedisLock("order:" + userId,stringRedisTemplate);
        // TODO 尝试获取锁
        boolean lock = redisLock.tryLock(1200);
        if(!lock) { // 获取锁失败, 直接返回错误信息
            return Result.fail("不允许重复抢购优惠券");
        }
        // ERR This locking method does not work in distributed or clustered environment

//        // TODO 给每个用户 ID 进行加锁, 只有相同用户才会阻塞
//        // NOTE intern() 保证是安装字符串的值来进行加锁的, 去字符串线程池查找有没有相同值的字符串
//        synchronized (userId.toString().intern()) {
//            // NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
//            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
//            // TODO 返回订单 id
//            return proxy.createVoucherOrder(voucherId);
//        }

        try {
            // NOTE 这里存在事务失效的问题, 需要用到代理对象调用该方法
            IVoucherOrderService proxy = (IVoucherOrderService) AopContext.currentProxy();
            // TODO 返回订单 id
            return proxy.createVoucherOrder(voucherId);
        } finally {
            // TODO 释放锁
            redisLock.unLock();
        }
    }

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
