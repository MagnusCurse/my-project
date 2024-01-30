package cn.itcast.order.web;

import cn.itcast.order.pojo.Order;
import cn.itcast.order.service.OrderService;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
public class OrderController {

   @Autowired
   private OrderService orderService;

    @SentinelResource("hot")
    @GetMapping("{orderId}")
    public Order queryOrderByUserId(@PathVariable("orderId") Long orderId,
                                    // 获取请求头中的参数 Truth
                                    @RequestHeader(value = "Truth", required = false) String truth) {
        System.out.println(truth);
        // 根据 id 查询订单并返回
        return orderService.queryOrderById(orderId);
    }

    @GetMapping("/query")
    public String queryOrder(){
        // 查询商品
        orderService.queryGoods();
        // 查询订单
        System.out.println("Query order");
        return "Query order successfully";
    }

    @GetMapping("/save")
    public String saveOrder() {
        // 查询商品
        orderService.queryGoods();
        // 查询订单
        System.err.println("Add order");
        return "Add order successfully";
    }

    @GetMapping("/update")
    public String updateOrder(){
        return "Update order successfully";
    }

}
