package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.pojo.MkScheduling;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-20 22:50
 **/

@Mapper
public interface MkSchedulingMapper extends BaseMapper<MkScheduling> {
}
