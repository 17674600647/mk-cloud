package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @program: mk-cloud
 * @description: 获取一个笔记
 * @author: lizhiyong
 * @create: 2022-03-06 19:16
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOneNoteDTO {
    @NotNull(message = "noteId不能为空！")
    @NotEmpty(message = "noteId不能为空格！")
    public String noteId;
}
