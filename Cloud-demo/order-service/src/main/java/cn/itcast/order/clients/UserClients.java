package cn.itcast.order.clients;

import cn.itcast.order.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("userservice")
public interface UserClients {
    @GetMapping("/user/{id}")
    User findById(@PathVariable("id") Long id);

}
