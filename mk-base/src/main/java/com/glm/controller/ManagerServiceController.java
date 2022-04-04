package com.glm.controller;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.UpdateNoteStatusDTO;
import com.glm.service.AdminNoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/manager")
@Api(tags = "管理员的基础服务")
@Log4j2
public class ManagerServiceController {
    @Autowired
    AdminNoteService adminNoteService;
    @PostMapping("/get/notes/by/status")
    @ApiOperation("分页查询文章列表更具文章状态")
    public ResponseResult getNote(@RequestBody @Valid GetNotesDTO getNote) {
        return adminNoteService.getPageNotesByShareStatus(getNote);
    }

    @PostMapping("/delete/one/note")
    @ApiOperation("删除文章")
    public ResponseResult deleteNote(@RequestBody @Valid GetOneNoteDTO deleteNote) {
        return adminNoteService.adminDeleteOneNote(deleteNote);
    }
    @PostMapping("/recover/one/note")
    @ApiOperation("恢复删除的文章")
    public ResponseResult recoverNote(@RequestBody @Valid GetOneNoteDTO deleteNote) {
        return adminNoteService.adminRecoverOneNote(deleteNote);
    }

    @PostMapping("/update/note/status")
    @ApiOperation("修改文章状态")
    public ResponseResult queryNoteStatus(@RequestBody @Valid UpdateNoteStatusDTO noteStatusDTO) {
        return adminNoteService.adminSetNoteStatus(noteStatusDTO);
    }

    @PostMapping("/query/note/data/report")
    @ApiOperation("查询文章数据")
    public ResponseResult queryNoteDataReport() {
        return adminNoteService.queryNoteDataReport();
    }

}
