package com.glm.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DataReportVO {
    /**
     * 没审核的
     */
    private Integer notReviewed;
    /**
     * 不通过的
     */
    private Integer notPassed;
    /**
     * 不通过率
     */
    private Integer notPassedRate;
}
