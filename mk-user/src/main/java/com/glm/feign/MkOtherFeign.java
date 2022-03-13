package com.glm.feign;

import com.glm.entity.ResponseResult;
import com.glm.feign.fallback.MkOtherFeignFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @program: mk-cloud
 * @description: feign调用myUser接口
 * @author: lizhiyong
 * @create: 2022-03-12 15:11
 **/

@FeignClient(value = "mk-other", fallback = MkOtherFeignFallBack.class)
public interface MkOtherFeign {
    @PostMapping(value = "/file/pic/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseResult picUpload(@RequestPart("file") MultipartFile file);
}
