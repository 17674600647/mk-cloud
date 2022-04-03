package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.UpdateNoteStatusDTO;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.vo.ObjectPageVO;
import com.glm.mapper.MkNoteMapper;
import com.glm.service.AdminNoteService;
import com.glm.utils.MkJwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminNoteServiceImpl implements AdminNoteService {
    @Autowired
    MkJwtUtil mkJwtUtil;
    @Autowired
    MkNoteMapper mkNoteMapper;

    @Override
    public ResponseResult getPageNotesByShareStatus(GetNotesDTO getNote) {
        if (getNote.getShareStatus().equals(-2)) {
            IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
            IPage<MkNotes> mkNotesIPage = mkNoteMapper.getAllDeleteNotes(notePage);
            ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
            notesPageVO.setTotal(mkNotesIPage.getTotal());
            notesPageVO.setNoteList(mkNotesIPage.getRecords());
            return ResponseResult.success("查询成功!", notesPageVO);
        }
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id", "share_status");

        if (!getNote.getShareStatus().equals(3)) {
            queryWrapper.eq("share_status", getNote.getShareStatus());
        }
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryWrapper);
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }


    @Override
    public ResponseResult adminDeleteOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.deleteById(Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("删除完成！");
    }

    @Override
    public ResponseResult adminRecoverOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.recoverOneNoteById(Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("恢复成功！");
    }

    @Override
    public ResponseResult adminSetNoteStatus(UpdateNoteStatusDTO updateNoteStatus) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", Long.valueOf(updateNoteStatus.getNoteId()));
        MkNotes mkNotes = new MkNotes();
        mkNotes.setShareStatus(updateNoteStatus.getShareStatus().shortValue());
        int update = mkNoteMapper.update(mkNotes, updateWrapper);
        if (update==1){
            return ResponseResult.success("更新成功");
        }
        return ResponseResult.error("更新失败");
    }
}
