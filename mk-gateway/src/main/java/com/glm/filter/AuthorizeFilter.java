package com.glm.filter;


import ch.qos.logback.core.util.TimeUtil;
import com.alibaba.cloud.commons.lang.StringUtils;

import com.glm.feign.MkBaseFeign;
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

import javax.annotation.PostConstruct;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Autowired
    MkUserFeign MkUserFeign;
    @Autowired
    MkBaseFeign mkBaseFeign;

    public static ConcurrentHashMap<String, List<Integer>> urlHashMap;


    @PostConstruct
    private void updateUrl() {
        if (Objects.isNull(urlHashMap)) {
            synchronized (AuthorizeFilter.class) {
                if (Objects.isNull(urlHashMap)) {
                    try{
                        Map<String, ArrayList<Integer>> stringListMap = mkBaseFeign.queryUrlAuth();
                        urlHashMap = new ConcurrentHashMap<>(stringListMap);
                        System.out.println("获取到Url权限");
                    }catch (Exception e){
                        System.out.println("未获取到Url权限");
                        urlHashMap = null;
                    }
                }
            }
        }
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getURI().getPath());
        if (request.getURI().getPath().contains("/manager/query/url/auth")){
            //放行
            return chain.filter(exchange);
        }
        //1获取请求对象,和响应对象
        ServerHttpResponse response = exchange.getResponse();
        while (Objects.isNull(urlHashMap)){
            try {
                updateUrl();
                System.out.println("尝试获取Url权限");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        List<Integer> integers = urlHashMap.get(request.getURI().getPath());
        if (integers == null) {
            updateUrl();
            integers = urlHashMap.get(request.getURI().getPath());
            if (integers==null){
                response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        //2判断当前请求是否不需要权限
        if (integers.contains(0)) {
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
        String role;
        try {
            //5如果令牌有效,解析JWT令牌,如果令牌无效返回错误
            role = MkUserFeign.verifyTokenRPC(token);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        if (Objects.isNull(role)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        Integer roleInt = Integer.valueOf(role);
        if (!integers.contains(roleInt)){
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
