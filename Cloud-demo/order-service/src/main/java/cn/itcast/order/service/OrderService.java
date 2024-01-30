package cn.itcast.order.service;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    // 由于不在 Spring 的默认扫描包下了，所以不会加入到容器中，需要修改启动类配置
    @Autowired
    private UserClient userClient;

    @SentinelResource("goods")
    public void queryGoods(){
        System.out.println("Query goods successfully");
    }


    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用 Feign 发起 Http 请求，查询用户对象
        User user = userClient.findById(order.getUserId());
        // 3.封装 User 到 Order
        order.setUser(user);
        // 4.返回
        return order;
    }

//    public Order queryOrderById(Long orderId) {
//        // 1.查询订单
//        Order order = orderMapper.findById(orderId);
//        // 2.利用 RestTemplate 查询用户信息
//        String url = "http://userservice/user/" + order.getUserId();
//        User user = restTemplate.getForObject(url,User.class);
//        order.setUser(user);
//        // 4.返回
//        return order;
//    }
}
