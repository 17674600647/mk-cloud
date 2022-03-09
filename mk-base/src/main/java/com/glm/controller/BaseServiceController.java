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
        return  noteService.saveNote(noteDTO);
    }

    @PostMapping("/get/notes")
    @ApiOperation("分页查询文章列表")
    public ResponseResult getNote(@RequestBody @Valid GetNotesDTO getNote) {
        return  noteService.getPageNotes(getNote);
    }
    @PostMapping("/get/one/note")
    @ApiOperation("查询一个文章")
    public ResponseResult getANote(@RequestBody @Valid GetOneNoteDTO getOneNoteDTO) {
        return  noteService.getOneNotes(getOneNoteDTO);
    }



}