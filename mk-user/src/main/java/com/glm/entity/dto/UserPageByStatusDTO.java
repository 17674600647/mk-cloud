package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPageByStatusDTO {
    @NotNull(message = "pageSize不能为空！")
    private Integer pageSize;
    @NotNull(message = "currentPage不能为空！")
    private Integer currentPage;
    private Integer status;
    private String content;
}
