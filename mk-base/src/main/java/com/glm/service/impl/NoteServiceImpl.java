package com.glm.service.impl;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.NoteDTO;
import com.glm.entity.pojo.MkNotes;
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
        mkNoteMapper.insert(mkNotes);
        return ResponseResult.success("保存成功!");
    }
}
