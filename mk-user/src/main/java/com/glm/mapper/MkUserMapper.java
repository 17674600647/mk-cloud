package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glm.entity.pojo.MkUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:49
 */

@Mapper
public interface MkUserMapper extends BaseMapper<MkUser> {

}
