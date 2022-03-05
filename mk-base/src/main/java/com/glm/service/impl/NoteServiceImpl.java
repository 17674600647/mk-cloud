package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.NoteDTO;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.vo.NotesPageVO;
import com.glm.mapper.MkNoteMapper;
import com.glm.service.NoteService;
import com.glm.utils.MkJwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: mk-cloud
 * @description: 用户服务层
 * @author: lizhiyong
 * @create: 2022-02-15 14:32
 **/

@Service
@Log4j2
public class NoteServiceImpl implements NoteService {
    @Autowired
    MkJwtUtil mkJwtUtil;
    @Autowired
    MkNoteMapper mkNoteMapper;


    @Override
    public ResponseResult saveNote(NoteDTO noteDTO) {
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        MkNotes mkNotes = new MkNotes();
        mkNotes.setContent(noteDTO.getContent());
        mkNotes.setTitle(noteDTO.getTitle());
        mkNotes.setUserId(userId);
        int result = mkNoteMapper.insert(mkNotes);
        if (result == 1) {
            return ResponseResult.success("保存成功!", mkNotes.getId());
        }
        return ResponseResult.error("保存失败!");
    }

    @Override
    public ResponseResult getPageNotes(GetNotesDTO getNote) {
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id")
                .eq("user_id", Long.valueOf(mkJwtUtil.getUserIdFromHeader()));
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryWrapper);
        NotesPageVO notesPageVO = new NotesPageVO();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }
}
