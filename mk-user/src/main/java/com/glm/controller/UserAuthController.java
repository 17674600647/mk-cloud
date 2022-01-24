package com.glm.controller;

import com.glm.exception.ControllerFieldAspect;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mk-cloud
 * @description: 用户身份验证
 * @author: lizhiyong
 * @create: 2022-01-23 20:26
 **/

@RestController
@RequestMapping("/auth")
@Api(tags = "用户身份验证接口")
@ControllerFieldAspect
public class UserAuthController {


}
