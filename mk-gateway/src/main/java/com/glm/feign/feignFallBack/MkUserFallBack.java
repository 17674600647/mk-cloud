package com.glm.feign.feignFallBack;

import com.glm.feign.MkUserFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-02-14 13:54
 **/

@Component
public class MkUserFallBack implements MkUserFeign {
    @Override
    public String verifyTokenRPC(@RequestBody String token) {
        return null;
    }
}
