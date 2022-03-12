package com.glm.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-02-14 13:53
 **/

@FeignClient(value = "mk-user", fallback = MkUserFallBack.class)
public interface MkUserFeign {
    @RequestMapping("/auth/rpc/tokenCheck")
    public String verifyTokenRPC( @RequestBody String token);
}
