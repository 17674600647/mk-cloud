package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {

    @NotNull(message = "account不能为空！")
    @NotEmpty(message = "account不能为空格！")
    private String account;
    @NotNull(message = "password不能为空!")
    @NotEmpty(message = "password不能为空格！")
    private String password;
}
