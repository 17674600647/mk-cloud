package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: mk-cloud
 * @description: 用户身份验证DTO
 * @author: lizhiyong
 * @create: 2022-02-13 17:17
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDto {
    @NotNull(message = "token不能为空！")
    @NotEmpty(message = "token不能为空格！")
    String token;
}
