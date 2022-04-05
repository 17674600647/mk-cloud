package com.glm.entity.vo;

import com.glm.entity.MkLogs;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogsVO {
    private Integer total;
    private List<MkLogs> logList = Collections.emptyList();
}
