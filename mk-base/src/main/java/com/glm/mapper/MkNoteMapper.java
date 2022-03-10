package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.glm.entity.pojo.MkNotes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper
public interface MkNoteMapper extends BaseMapper<MkNotes> {

    @Select("SELECT id,title,create_time,update_time,classic,user_id FROM mk_notes " +
            "WHERE deleted = 1 " +
            "AND user_id = #{user_id} ORDER BY create_time DESC")
    public IPage<MkNotes> getDeleteNotes(IPage<MkNotes> notes,@Param("user_id") Long user_id);

    @Select("SELECT id,content,title,create_time,update_time,classic,user_id FROM mk_notes WHERE id = #{id}" )
    public MkNotes getOneNotes(@Param("id") Long id);

    @Update("update mk_notes set deleted =0 where id=#{id}")
    public void recoverOneNote(@Param("id") Long id);
}
