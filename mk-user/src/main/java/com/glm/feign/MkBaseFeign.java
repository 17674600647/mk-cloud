package com.glm.feign;


import com.glm.entity.ResponseResult;

import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.feign.fallback.MkBaseFeignFallBack;

import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @program: mk-cloud
 * @description: base的远程调用
 * @author: lizhiyong
 * @create: 2022-03-22 22:46
 **/
@FeignClient(value = "mk-base", fallback = MkBaseFeignFallBack.class)
public interface MkBaseFeign {
    @RequestMapping(value = "/base/rpc/get/one/note/userid", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseResult getANote(@RequestBody GetOneNoteDTO getOneNoteDTO);
}
