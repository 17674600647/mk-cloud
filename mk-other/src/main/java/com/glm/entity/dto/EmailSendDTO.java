package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @program: mk-cloud
 * @description: 发送邮件的DTO类
 * @author: lizhiyong
 * @create: 2022-01-23 22:08
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSendDTO {
    @NotNull(message = "邮箱不能为空！")
    @NotEmpty(message = "邮箱不能为空格！")
    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")
    private String email;
}
