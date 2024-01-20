package cn.itcast.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1) // 当有多个过滤器的时候，需要指定顺序
public class AuthorizationFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 获取请求参数
        MultiValueMap<String,String> params =
                exchange.getRequest().getQueryParams();
        // 2. 获取 Authorization 参数
        String auth = params.getFirst("authorization");
        // 3. 校验
        if("admin".equals(auth)) {
            // 放行
            return chain.filter(exchange);
        }
        // 4. 拦截
        // 4.1 禁止访问，设置状态码
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        // 4.2 结束处理
        return exchange.getResponse().setComplete();
    }
}
