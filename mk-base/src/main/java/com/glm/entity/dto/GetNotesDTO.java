package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @program: mk-cloud
 * @description: 分页查询DTO类
 * @author: lizhiyong
 * @create: 2022-03-05 17:04
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetNotesDTO {
    @NotNull(message = "查询的pageSize不能为空")
    private Integer pageSize;
    @NotNull(message = "查询的currentPage不能为空")
    private Integer currentPage;

    private Integer shareStatus;

    private Long noteTypeId;
}
