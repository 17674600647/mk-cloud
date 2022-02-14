package com.glm.filter;


import com.alibaba.cloud.commons.lang.StringUtils;

import com.glm.feign.MkUserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;


@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Autowired
    MkUserFeign MkUserFeign;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getURI().getPath());
        //1获取请求对象,和响应对象
        ServerHttpResponse response = exchange.getResponse();
        //2判断当前请求是否位登录,
        if (request.getURI().getPath().contains("/base/login")) {
            //放行
            return chain.filter(exchange);
        }
        //3获取用户请求头中的token
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst("token");
        //4判断当前token是否存在
        if (StringUtils.isEmpty(token)) {
            //如果不存在
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        String code;
        try {
            //5如果令牌有效,解析JWT令牌,如果令牌无效返回错误
            code = MkUserFeign.verifyTokenRPC(token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        if (!"200".equals(code)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //6.放行
        return chain.filter(exchange);
    }

    /**
     * 优先级设置,值越小优先级越高
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
