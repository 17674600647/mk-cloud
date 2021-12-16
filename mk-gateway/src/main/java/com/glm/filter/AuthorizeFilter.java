package com.glm.filter;

import lombok.extern.log4j.Log4j2;
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


@Component
@Log4j2
public class AuthorizeFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
       /* //1获取请求对象,和响应对象
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //2判断当前请求是否位登录,
        if (request.getURI().getPath().contains("/login/in")) {
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
        try {
            //5如果令牌有效,解析JWT令牌,如果令牌无效返回错误
            Claims claims = AppJwtUtil.getClaimsBody(token);
            int result = AppJwtUtil.verifyToken(claims);

            if (result == 0 || result == -1) {
                Integer id = (Integer) claims.get("id");
                log.info("find userid;{} from uri:{}", id, request.getURI());
                //重新设置token
                ServerHttpRequest serverHttpRequest = request.mutate().headers(httpHeaders -> {
                    httpHeaders.add("userId", id + "");
                }).build();
                //5.1合法向header中重新设置id
                exchange.mutate().request(serverHttpRequest).build();
            }
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //6.放行
        return chain.filter(exchange);*/
        return null;
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
