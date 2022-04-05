package com.glm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.config.exception.MessageException;
import com.glm.entity.MkLogs;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.NoteDTO;

import com.glm.entity.enums.MkLogEnum;
import com.glm.entity.pojo.MkCollect;
import com.glm.entity.pojo.MkNotes;

import com.glm.entity.vo.ObjectPageVO;
import com.glm.mapper.MkCollectMapper;
import com.glm.mapper.MkNoteMapper;
import com.glm.service.NoteService;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.MkKafkaUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mk-cloud
 * @description: 用户服务层
 * @author: lizhiyong
 * @create: 2022-02-15 14:32
 **/

@Service
@Log4j2
@Transactional
public class NoteServiceImpl implements NoteService {
    @Autowired
    MkJwtUtil mkJwtUtil;
    @Autowired
    MkNoteMapper mkNoteMapper;

    @Autowired
    MkKafkaUtil mkKafkaUtil;

    @Autowired
    MkCollectMapper mkCollectmapper;

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
                mkKafkaUtil.send(MkLogs.mkLogsByMkLogEnum(MkLogEnum.NEW_NOTE,userId));
                return ResponseResult.success("保存成功!", String.valueOf(mkNotes.getId()));
            }
            return ResponseResult.error("保存失败!");
        } else {
            MkNotes oneNotes = mkNoteMapper.getOneNotes(Long.valueOf(noteDTO.getNoteId()));
            if (!oneNotes.getUserId().equals(userId)) {
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
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.getDeleteNotesById(notePage, Long.valueOf(mkJwtUtil.getUserIdFromHeader()));
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }

    @Override
    public ResponseResult getOneNotes(GetOneNoteDTO getNote) {
        MkNotes mkNotes = mkNoteMapper.getOneNotes(Long.valueOf(getNote.getNoteId()));
        Long aLong = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        if (!mkNotes.getUserId().equals(aLong) && mkNotes.getShareStatus() == -1) {
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
        mkKafkaUtil.send(MkLogs.mkLogsByMkLogEnum(MkLogEnum.DELETE_NOTE_SELF,userId));
        return ResponseResult.success("删除成功！");
    }




    @Override
    public ResponseResult recoverOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.recoverOneNoteById(Long.valueOf(getNote.getNoteId()));
        MkNotes mkNotes = mkNoteMapper.selectOne(Wrappers.<MkNotes>query()
                .select("user_id")
                .eq("id", Long.valueOf(getNote.getNoteId())));
        Long userId = Long.valueOf(mkJwtUtil.getUserIdFromHeader());
        if (!userId.equals(mkNotes.getUserId())) {
            throw new MessageException("身份存在错误");
        }
        return ResponseResult.success("恢复成功！");
    }

    @Override
    public ResponseResult getSharedNotesApi(GetNotesDTO getNote) {
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id", "share_status")
                .eq("user_id", Long.valueOf(mkJwtUtil.getUserIdFromHeader()))
                .and((wrapper) -> {
                    wrapper.eq("share_status", MKNOTE_SHARED);
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
    public ResponseResult toCollectNote(GetOneNoteDTO getNote) {
        String userIdFromHeader = mkJwtUtil.getUserIdFromHeader();
        QueryWrapper<MkCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Long.valueOf(userIdFromHeader))
                .eq("note_id", Long.valueOf(getNote.getNoteId()));
        List<MkCollect> mkCollects = mkCollectmapper.selectList(queryWrapper);
        if (mkCollects.size() > 0) {
            throw new MessageException("请勿重复收藏~");
        }
        mkCollectmapper.insert(new MkCollect().setNoteId(Long.valueOf(getNote.getNoteId())).setUserId(Long.valueOf(userIdFromHeader)));
        return ResponseResult.success("收藏成功~");
    }

    @Override
    public ResponseResult toDisCollect(GetOneNoteDTO getNote) {
        String userIdFromHeader = mkJwtUtil.getUserIdFromHeader();
        QueryWrapper<MkCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", Long.valueOf(userIdFromHeader));
        queryWrapper.eq("note_id", Long.valueOf(getNote.getNoteId()));
        List<MkCollect> mkCollects = mkCollectmapper.selectList(queryWrapper);
        if (mkCollects.size() < 1) {
            throw new MessageException("收藏记录不存在");
        }
        mkCollectmapper.deleteCollect(Long.valueOf(userIdFromHeader), Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("取消收藏成功~");
    }

    @Override
    public ResponseResult toDishareNote(GetOneNoteDTO getNote) {
        changeShareStatus(getNote, MKNOTE_DISHARE);
        return ResponseResult.success("已删除分享");
    }

    @Override
    public ResponseResult queryCollectNotes(GetNotesDTO getNote) {
        String user_id = mkJwtUtil.getUserIdFromHeader();
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkCollect> queryCollectNotes = new QueryWrapper<>();
        queryCollectNotes.eq("user_id", Long.valueOf(user_id));
        List<MkCollect> mkCollects = mkCollectmapper.selectList(queryCollectNotes);
        if (mkCollects.size() < 1) {
            return ResponseResult.success("暂无数据!");
        }
        //查询出所有的noteId
        List<Long> noteIds = mkCollects.stream().map(MkCollect::getNoteId).collect(Collectors.toList());
        QueryWrapper<MkNotes> queryNotes = Wrappers.<MkNotes>query()
                .select("id", "title", "create_time", "update_time")
                .eq("share_status", 1)
                .in("id", noteIds);
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryNotes);
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
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


    @Override
    public ResponseResult getOneNotesRpc(GetOneNoteDTO getNote) {
        MkNotes mkNotes = mkNoteMapper.getOneNotes(Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("查询成功!", mkNotes);
    }
}
