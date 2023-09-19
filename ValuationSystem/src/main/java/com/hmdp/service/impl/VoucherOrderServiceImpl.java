package com.hmdp.service.impl;

import com.hmdp.dto.Result;
import com.hmdp.entity.SeckillVoucher;
import com.hmdp.entity.VoucherOrder;
import com.hmdp.mapper.VoucherOrderMapper;
import com.hmdp.service.ISeckillVoucherService;
import com.hmdp.service.IVoucherOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmdp.utils.RedisConstants;
import com.hmdp.utils.RedisWorker;
import com.hmdp.utils.UserHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;


@Service
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder> implements IVoucherOrderService {

    @Resource
    private ISeckillVoucherService seckillVoucherService;

    @Resource
    private RedisWorker redisWorker;

    /**
     * 采用乐观锁解决了超卖问题: stock 作为版本号
     * @param voucherId
     * @return
     */
    @Transactional
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
        long userId = UserHolder.getUser().getId();
        voucherOrder.setUserId(userId);
        // 代金券 id
        voucherOrder.setVoucherId(voucherId);
        // 将代金券订单保存到数据库中
        save(voucherOrder);
        // TODO 返回订单 id
        return Result.ok(orderId);
    }
}
