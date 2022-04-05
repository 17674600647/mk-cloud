package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryLogsPageDTO {
    @NotNull(message = "查询的每页大小不能为空")
    private Integer pageSize;
    @NotNull(message = "查询的页数不能为空")
    private Integer currentPage;
    /**
     * 关键字
     */
    private String keyWords;
    @NotNull(message = "level不能为空")
    private Integer level;
}
