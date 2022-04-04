package com.glm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.glm.entity.pojo.DataTakeOver;
import com.glm.entity.pojo.MkNotes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Mapper
public interface MkNoteMapper extends BaseMapper<MkNotes> {

    @Select("SELECT id,title,create_time,update_time,classic,user_id FROM mk_notes " +
            "WHERE deleted = 1 " +
            "AND user_id = #{user_id} ORDER BY create_time DESC")
    public IPage<MkNotes> getDeleteNotesById(IPage<MkNotes> notes, @Param("user_id") Long user_id);

    @Select("SELECT id,title,create_time,update_time,classic,user_id FROM mk_notes " +
            "WHERE deleted = 1 " +
            "ORDER BY create_time DESC")
    public IPage<MkNotes> getAllDeleteNotes(IPage<MkNotes> notes);

    @Select("SELECT id,content,title,create_time,update_time,classic,user_id FROM mk_notes WHERE id = #{id}")
    public MkNotes getOneNotes(@Param("id") Long id);

    @Update("update mk_notes set deleted =0 where id=#{id}")
    public void recoverOneNoteById(@Param("id") Long id);

    @Select("Select  share_status,count(share_status) as total\n" +
            "from mk_notes\n" +
            "where deleted = 0 " +
            "group by share_status;")
    public List<DataTakeOver> queryDataReport();


}
