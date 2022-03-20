package com.glm.service.impl;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.SearchNotesDTO;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.vo.ObjectPageVO;
import com.glm.service.NoteSearchService;
import com.glm.utils.EsUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-19 16:37
 **/

@Service
@Log4j2
public class NoteSearchServiceImpl implements NoteSearchService {

    @Autowired
    EsUtil esUtil;

    @Override
    public ResponseResult searchSharedNotes(SearchNotesDTO searchNotes) {
        SearchHits<MkNotes> searchHits = esUtil.searchNotesOfTitleAndContent(searchNotes.getSearchContent(), searchNotes.getCurrentPage(), searchNotes.getPageSize());
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>()
                .setTotal(searchHits.getTotalHits())
                .setNoteList(searchHits.stream()
                        .map(SearchHit::getContent)
                        .collect(Collectors.toList()));
        return ResponseResult.success("查询成功~", notesPageVO);
    }

    @Override
    public ResponseResult searchSharedNoteTitles(SearchNotesDTO searchNotes) {
        SearchHits<MkNotes> searchHits = esUtil.searchNotesOfTitle(searchNotes.getSearchContent());
        ObjectPageVO<String> objectPageVO = new ObjectPageVO<String>()
                .setTotal(searchHits.getTotalHits())
                .setNoteList(searchHits.stream().map((SearchHit<MkNotes> searchHitX) -> {
                    return searchHitX.getContent().getTitle();
                }).collect(Collectors.toList()));
        return ResponseResult.success("查询成功~", objectPageVO);
    }
}
