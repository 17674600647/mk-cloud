package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.QueryLogsPageDTO;

public interface LogsService {
    public ResponseResult queryLogsByLevel(QueryLogsPageDTO queryLogsPage);
}
