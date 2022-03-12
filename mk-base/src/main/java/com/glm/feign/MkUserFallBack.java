package com.glm.feign;

import com.glm.entity.ResponseResult;
import com.glm.feign.MkUserFeign;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-02-14 18:53
 **/


public class MkUserFallBack implements MkUserFeign {
    @Override
    public String verifyTokenRPC(String token) {
        return "400";
    }
}
