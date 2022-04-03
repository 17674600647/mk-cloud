package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryUserInfoByIdDTO {
    @NotBlank(message = "用户名不能为空")
    public String userId;
}
