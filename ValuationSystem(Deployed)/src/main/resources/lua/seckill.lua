-- 1. 参数列表
-- 1.1 优惠券 id
local voucherId = ARGV[1]
-- 1.2 用户 id
local userId = ARGV[2]
-- 1.3 订单 id
local orderId = ARGV[3]

-- 2. 数据 key
-- 2.1 库存 key (lua 脚本连接字符串 ..)
local stockKey = 'seckill:stock:' .. voucherId
-- 2.2 订单 key
local orderKey = 'seckill:order:' .. userId

--3. lua 脚本业务
--3.1 判断库存是否充足
if(tonumber(redis.call('get',stockKey)) <= 0) then
    -- 3.2 库存不足, 返回 1
    return 1
end
-- 3.2 判断用户是否已经下单
if(redis.call('sismember',orderKey,userId) == 1) then
    -- 3.3 重复下单, 返回 2
    return 2
end
-- 3.4 扣减库存
redis.call('incrby', stockKey, -1)
-- 3.5 下单 (保存用户)
redis.call('sadd', orderKey, userId, orderId)
-- 3.6 将消息发送到消息队列中, XADD stream.orders * k1 v1 k2 v2 ......
-- 注意这里要写 id , 因为最后是要存入数据库的, 数据库表字段是 id
redis.call('xadd','stream.orders','*','userId',userId,'voucherId',voucherId,'id',orderId)
return 0
