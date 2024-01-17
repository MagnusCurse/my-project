package cn.itcast.order.service;

import cn.itcast.order.clients.UserClients;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import cn.itcast.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserClients userClients;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用 Feign 发起 Http 请求，查询用户对象
        User user = userClients.findById(order.getUserId());
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
