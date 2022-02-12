package com.glm.controller;

import com.glm.exception.ControllerFieldAspect;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mk-cloud
 * @description: 基础服务接口
 * @author: lizhiyong
 * @create: 2022-02-12 18:16
 **/

@RestController
@RequestMapping("/base")
@Api(tags = "用户基础服务")
@ControllerFieldAspect
@Log4j2
public class BaseServiceController {
}
