package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.config.exception.MessageException;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.NoteDTO;

import com.glm.entity.pojo.MkNotes;

import com.glm.entity.vo.ObjectPageVO;
import com.glm.mapper.MkNoteMapper;
import com.glm.service.NoteService;
import com.glm.utils.MkJwtUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
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
    public static Short MKNOTE_SHARED = 1;
    public static Short MKNOTE_CHECK = 0;
    public static Short MKNOTE_DISHARE = -1;

    @Override
    public ResponseResult saveNote(NoteDTO noteDTO) {
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        MkNotes mkNotes = new MkNotes();
        mkNotes.setContent(noteDTO.getContent());
        mkNotes.setTitle(noteDTO.getTitle());
        mkNotes.setUserId(userId);
        if (StringUtils.isEmpty(noteDTO.getNoteId())) {
            int result = mkNoteMapper.insert(mkNotes);
            if (result == 1) {
                return ResponseResult.success("保存成功!", String.valueOf(mkNotes.getId()));
            }
            return ResponseResult.error("保存失败!");
        } else {
            MkNotes oneNotes = mkNoteMapper.getOneNotes(Long.valueOf(noteDTO.getNoteId()));
            if (!oneNotes.getUserId().equals(userId)){
                return ResponseResult.error("身份验证失败!");
            }
            mkNotes.setId(Long.valueOf(noteDTO.getNoteId()));
            mkNotes.setShareStatus(MKNOTE_DISHARE);
            UpdateWrapper<MkNotes> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", userId)
                    .eq("id", Long.valueOf(noteDTO.getNoteId()));
            int result = mkNoteMapper.update(mkNotes, updateWrapper);
            if (result == 1) {
                return ResponseResult.success("更新成功!", String.valueOf(mkNotes.getId()));
            }
            return ResponseResult.error("保存更新失败!");
        }

    }

    @Override
    public ResponseResult getPageNotes(GetNotesDTO getNote) {
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id", "share_status")
                .eq("user_id", Long.valueOf(mkJwtUtil.getUserIdFromHeader()));
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryWrapper);
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }

    @Override
    public ResponseResult getPageDeleteNotes(GetNotesDTO getNote) {
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.getDeleteNotes(notePage, Long.valueOf(mkJwtUtil.getUserIdFromHeader()));
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }

    @Override
    public ResponseResult getOneNotes(GetOneNoteDTO getNote) {
        MkNotes mkNotes = mkNoteMapper.getOneNotes(Long.valueOf(getNote.getNoteId()));
        if (!mkNotes.getUserId().equals(Long.valueOf(mkJwtUtil.getUserIdFromHeader()))&&mkNotes.getShareStatus()==-1) {
            return ResponseResult.error("身份存在错误");
        }
        return ResponseResult.success("查询成功!", mkNotes);
    }

    @Override
    public ResponseResult deleteOneNote(GetOneNoteDTO getNote) {
        MkNotes mkNotes = mkNoteMapper.selectOne(Wrappers.<MkNotes>query()
                .select("deleted", "user_id")
                .eq("id", Long.valueOf(getNote.getNoteId())));
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        if (!userId.equals(mkNotes.getUserId())) {
            return ResponseResult.error("身份存在错误");
        }
        mkNoteMapper.deleteById(Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("删除成功！");
    }

    @Override
    public ResponseResult recoverOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.recoverOneNote(Long.valueOf(getNote.getNoteId()));
        MkNotes mkNotes = mkNoteMapper.selectOne(Wrappers.<MkNotes>query()
                .select("user_id")
                .eq("id", Long.valueOf(getNote.getNoteId())));
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        if (!userId.equals(mkNotes.getUserId())) {
            return ResponseResult.error("身份存在错误");
        }
        return ResponseResult.success("恢复成功！");
    }

    @Override
    public ResponseResult getSharedNotesApi(GetNotesDTO getNote) {
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id","share_status")
                .eq("user_id", Long.valueOf(mkJwtUtil.getUserIdFromHeader()))
                .and((wrapper) -> {
                    wrapper.eq("share_status", MKNOTE_SHARED)
                            .or()
                            .eq("share_status", MKNOTE_CHECK);
                });
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryWrapper);
        return ResponseResult.success("查询成功!", new ObjectPageVO<MkNotes>(mkNotesIPage.getTotal(), mkNotesIPage.getRecords()));
    }

    @Override
    public ResponseResult toShareNote(GetOneNoteDTO getNote) {
        changeShareStatus(getNote, MKNOTE_CHECK);
        return ResponseResult.success("已添加分享，待审核！");
    }

    @Override
    public ResponseResult toDishareNote(GetOneNoteDTO getNote) {
        changeShareStatus(getNote, MKNOTE_DISHARE);
        return ResponseResult.success("已删除分享");
    }

    public void changeShareStatus(GetOneNoteDTO getNote, Short status) {
        MkNotes mkNotes = mkNoteMapper.selectOne(Wrappers.<MkNotes>query()
                .select("share_status", "user_id")
                .eq("id", Long.valueOf(getNote.getNoteId())));
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        if (!userId.equals(mkNotes.getUserId())) {
            throw new MessageException("用户信息不匹配~");
        }
        mkNotes.setShareStatus(status);
        UpdateWrapper<MkNotes> updateWrapper = new UpdateWrapper<MkNotes>();
        updateWrapper.eq("id", Long.valueOf(getNote.getNoteId()));
        mkNoteMapper.update(mkNotes, updateWrapper);
    }
}
