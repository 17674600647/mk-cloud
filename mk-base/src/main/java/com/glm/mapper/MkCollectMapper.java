package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.glm.entity.pojo.MkCollect;
import com.glm.entity.vo.CollectNoteVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @program: mk-cloud
 * @description: 用户
 * @author: lizhiyong
 * @create: 2022-03-24 21:36
 **/
@Mapper
public interface MkCollectMapper extends BaseMapper<MkCollect> {

    @Delete(value = "delete from mk_collect where user_id=#{user_id} and note_id=#{note_id}")
    public void deleteCollect(@Param("user_id") Long user_id, @Param("note_id") Long note_id);

    @Select(value = "select  note_id as key,count(note_id) as score from mk_collect\n" +
            "group by note_id order by score desc  limit 10;")
    public List<CollectNoteVO> countCollectNumb();
}
