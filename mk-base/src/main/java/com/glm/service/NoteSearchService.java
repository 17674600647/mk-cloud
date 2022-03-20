package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.SearchNotesDTO;
import com.glm.service.impl.NoteSearchServiceImpl;

/**
 * @program: mk-cloud
 * @description: 文章搜索服务
 * @author: lizhiyong
 * @create: 2022-03-19 16:37
 **/
public interface NoteSearchService {
    public ResponseResult searchSharedNotes(SearchNotesDTO searchNotes);
    public ResponseResult searchSharedNoteTitles(SearchNotesDTO searchNotes);
}
