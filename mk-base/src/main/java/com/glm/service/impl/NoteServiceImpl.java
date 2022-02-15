package com.glm.service.impl;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.NoteDTO;
import com.glm.service.NoteService;
import lombok.extern.log4j.Log4j2;
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
    @Override
    public ResponseResult saveNote(NoteDTO noteDTO) {
        return null;
    }
}
