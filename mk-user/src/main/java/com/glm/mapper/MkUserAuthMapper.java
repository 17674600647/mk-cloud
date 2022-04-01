package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glm.entity.pojo.MkUserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MkUserAuthMapper extends BaseMapper<MkUserAuth> {
    @Select(value = "select role\n" +
            "from mk_user_auth mua,mk_auth ma\n" +
            "where mua.user_id =#{useId} and mua.auth_id=ma.id")
    List<Integer> findUserAuthMark(@Param("useId") Long userId);
}
