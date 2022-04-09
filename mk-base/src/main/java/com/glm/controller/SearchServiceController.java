package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.NoteDTO;
import com.glm.entity.dto.SearchNotesDTO;
import com.glm.service.NoteSearchService;
import com.glm.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: mk-cloud
 * @description: 搜索服务接口
 * @author: lizhiyong
 * @create: 2022-03-19 15:08
 **/


@RestController
@RequestMapping("/search")
@Api(tags = "用户搜索服务")
@Log4j2
public class SearchServiceController {
    @Autowired
    NoteService noteService;
    @Autowired
    NoteSearchService noteSearchService;


    @PostMapping("/share/notes")
    @ApiOperation("搜索分享的文章标题与内容")
    public ResponseResult searchNotes(@Valid @RequestBody SearchNotesDTO searchNotes) {
        return noteSearchService.searchSharedNotes(searchNotes);
    }

    @PostMapping("/share/notes/title")
    @ApiOperation("搜索分享的文章标题")
    public ResponseResult searchNotesTitles(@Valid @RequestBody SearchNotesDTO searchNotes) {
        return noteSearchService.searchSharedNoteTitles(searchNotes);
    }
}
