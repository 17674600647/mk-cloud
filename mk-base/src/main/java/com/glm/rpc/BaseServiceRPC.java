package com.glm.rpc;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetOneNoteDTO;
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
 * @description: Rpc框架
 * @author: lizhiyong
 * @create: 2022-03-23 21:48
 **/

@RestController
@RequestMapping("/base/rpc")
@Api(tags = "用户基础服务")
@Log4j2
public class BaseServiceRPC {
    @Autowired
    NoteService noteService;

    @PostMapping("/get/one/note/userid")
    @ApiOperation("查询一个文章的id")
    public ResponseResult getANote(@RequestBody @Valid GetOneNoteDTO getOneNoteDTO) {
        return noteService.getOneNotesRpc(getOneNoteDTO);
    }
}
