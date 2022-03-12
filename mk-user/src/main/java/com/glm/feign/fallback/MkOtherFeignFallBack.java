package com.glm.feign.fallback;

import com.glm.entity.ResponseResult;
import com.glm.feign.MkOtherFeign;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: mk-cloud
 * @description: myuser的Feign的返回类
 * @author: lizhiyong
 * @create: 2022-03-12 15:12
 **/

@Component
public class MkOtherFeignFallBack implements MkOtherFeign {
    @Override
    public ResponseResult picUpload(MultipartFile file) {
        return ResponseResult.error("调用other服务失败~");
    }
}
