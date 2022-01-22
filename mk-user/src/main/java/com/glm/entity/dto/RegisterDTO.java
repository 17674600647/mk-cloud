package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RegisterDTO {
    @NotNull(message = "username不能为空！")
    @NotEmpty(message = "username不能为空格！")
    @Pattern(regexp = "^[a-z0-9_-]{6,16}$")
    private String username;
    @NotNull(message = "密码不能为空！")
    @NotEmpty(message = "密码不能为空格！")
    @Pattern(regexp="^[a-z0-9_-]{6,18}$")
    private String password;
    @NotNull(message = "确认密码不能为空！")
    @NotEmpty(message = "确认密码不能为空格！")
    @Pattern(regexp="^[a-z0-9_-]{6,18}$")
    private String password2;
    @NotNull(message = "邮箱不能为空！")
    @NotEmpty(message = "邮箱不能为空格！")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
    private String email;

    @NotNull(message = "验证码不能为空！")
    @NotEmpty(message = "验证码不能为空格！")
    @Pattern(regexp = "^\\d{5}$")
    private String checkCode;

}
