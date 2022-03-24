package com.glm.controller;

import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.NoteDTO;
import com.glm.entity.ResponseResult;
import com.glm.exception.ControllerFieldAspect;
import com.glm.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: mk-cloud
 * @description: 基础服务接口
 * @author: lizhiyong
 * @create: 2022-02-12 18:16
 **/

@RestController
@RequestMapping("/base")
@Api(tags = "用户基础服务")
@Log4j2
public class BaseServiceController {
    @Autowired
    NoteService noteService;

    @PostMapping("/save/note")
    @ApiOperation("保存/更新文章")
    public ResponseResult saveNote(@RequestBody @Valid NoteDTO noteDTO) {
        return noteService.saveNote(noteDTO);
    }

    @PostMapping("/get/notes")
    @ApiOperation("分页查询文章列表")
    public ResponseResult getNote(@RequestBody @Valid GetNotesDTO getNote) {
        return noteService.getPageNotes(getNote);
    }

    @PostMapping("/get/one/note")
    @ApiOperation("查询一个文章")
    public ResponseResult getANote(@RequestBody @Valid GetOneNoteDTO getOneNoteDTO) {
        return noteService.getOneNotes(getOneNoteDTO);
    }

    @PostMapping("/delete/one/note")
    @ApiOperation("删除文章")
    public ResponseResult deleteNote(@RequestBody @Valid GetOneNoteDTO deleteNote) {
        return noteService.deleteOneNote(deleteNote);
    }

    @PostMapping("/get/delete/notes")
    @ApiOperation("分页查询被删除的文章列表")
    public ResponseResult getDeleteNotesApi(@RequestBody @Valid GetNotesDTO getDeleteNote) {
        return noteService.getPageDeleteNotes(getDeleteNote);
    }

    @PostMapping("/recover/one/note")
    @ApiOperation("恢复删除的文章")
    public ResponseResult recoverNote(@RequestBody @Valid GetOneNoteDTO deleteNote) {
        return noteService.recoverOneNote(deleteNote);
    }

    @PostMapping("/get/shared/notes")
    @ApiOperation("分页查询/审核中/分享的文章列表")
    public ResponseResult getSharedNotesApi(@RequestBody @Valid GetNotesDTO getDeleteNote) {
        return noteService.getSharedNotesApi(getDeleteNote);
    }
    @PostMapping("/to/dishare/note")
    @ApiOperation("取消分享文章")
    public ResponseResult toDishareNote(@RequestBody @Valid GetOneNoteDTO getDisShareNote) {
        return noteService.toDishareNote(getDisShareNote);
    }

    @PostMapping("/to/share/note")
    @ApiOperation("分享一个文章")
    public ResponseResult toShareNote(@RequestBody @Valid GetOneNoteDTO toShareNote) {
        return noteService.toShareNote(toShareNote);
    }

    @PostMapping("/to/collect/note")
    @ApiOperation("收藏一个文章")
    public ResponseResult toCollect(@RequestBody @Valid GetOneNoteDTO CollectNote) {
        return noteService.toCollectNote(CollectNote);
    }

    @PostMapping("/to/disCollect/note")
    @ApiOperation("取消收藏一个文章")
    public ResponseResult toDisCollect(@RequestBody @Valid GetOneNoteDTO disCollectNote) {
        return noteService.toDisCollect(disCollectNote);
    }

    @PostMapping("/query/collect/notes")
    @ApiOperation("查询收藏的文章")
    public ResponseResult queryCollectNotes(@RequestBody @Valid  GetNotesDTO getNote) {
        return noteService.queryCollectNotes(getNote);
    }
}
