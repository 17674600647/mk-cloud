package com.glm.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetOneNoteDTO {
    @NotNull(message = "noteId不能为空！")
    @NotEmpty(message = "noteId不能为空格！")
    public String noteId;
}