package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class RegisterDTO {
    @NotNull(message = "username不能为空！")
    @NotEmpty(message = "username不能为空格！")
    @Pattern(regexp = "^[a-z0-9_-]{6,16}$",message="用户名格式不正确~")
    private String username;

    @NotNull(message = "密码不能为空！")
    @NotEmpty(message = "密码不能为空格！")
    private String password;

    @NotNull(message = "确认密码不能为空！")
    @NotEmpty(message = "确认密码不能为空格！")
    private String password2;

    @NotNull(message = "邮箱不能为空！")
    @NotEmpty(message = "邮箱不能为空格！")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$",message="邮箱格式不正确~")
    private String email;

    @NotNull(message = "验证码不能为空！")
    @NotEmpty(message = "验证码不能为空格！")
    @Pattern(regexp = "^\\d{5}$",message="验证码不正确~")
    private String checkCode;

}
