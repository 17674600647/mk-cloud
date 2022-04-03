package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserStatesDTO {
    @NotBlank(message = "用户名不能为空")
    @NotNull(message = "用户名不能为空")
    public String userId;

    @NotNull(message = "状态不能为空")
    public Integer status;
}
