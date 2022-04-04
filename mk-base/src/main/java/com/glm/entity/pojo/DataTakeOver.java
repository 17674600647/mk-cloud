package com.glm.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataTakeOver {
    /**
     * 文章状态
     */
    private Integer shareStatus;
    /**
     * 总共数据
     */
    private Integer total;
}
