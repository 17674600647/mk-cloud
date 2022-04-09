package com.glm.feign;

import com.glm.feign.feignFallBack.MkBaseFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;


@FeignClient(value = "mk-base", fallback = MkBaseFallBack.class)
public interface MkBaseFeign {
    @PostMapping("/manager/query/url/auth")
    public Map<String, List<Integer>> queryUrlAuth();

}
