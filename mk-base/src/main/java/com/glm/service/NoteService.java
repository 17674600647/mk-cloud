package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.NoteDTO;

/**
 * @program: mk-cloud
 * @description: 笔记服务层
 * @author: lizhiyong
 * @create: 2022-02-15 14:32
 **/
public interface NoteService {
    public ResponseResult saveNote(NoteDTO noteDTO);
    public ResponseResult getPageNotes(GetNotesDTO getNote );
}
