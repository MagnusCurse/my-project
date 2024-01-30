package cn.itcast.feign.clients.fallback;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class UserClinetFallbackFactory implements FallbackFactory<UserClient> {

    @Override
    public UserClient create(Throwable throwable) {
        return new UserClient() {
            @Override
            public User findById(Long id) {
                log.error("查询用户出现异常",throwable);
                return new User();
            }
        };
    }
}
