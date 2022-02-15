package com.glm.feign;

import com.glm.feign.fallback.MkUserFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: mk-cloud
 * @description: mk-user的fegin的接口
 * @author: lizhiyong
 * @create: 2022-02-14 18:51
 **/
@FeignClient(value = "mk-user", fallback = MkUserFallBack.class)
public interface MkUserFeign {
    @RequestMapping("/auth/rpc/tokenCheck")
    public String verifyTokenRPC( @RequestBody String token);
}