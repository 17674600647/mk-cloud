package com.glm.entity.dto;

import com.glm.entity.pojo.MkType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Collections;
import java.util.List;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

/**
 * @program: mk-cloud
 * @description: 笔记保存DTO,如果笔记的id为空,表示新建
 * @author: lizhiyong
 * @create: 2022-02-13 16:26
 **/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {
    private String noteId;
    @NotNull(message = "title不能为空！")
    @NotEmpty(message = "title不能为空格！")
    private String title;
    @NotNull(message = "content不能为空！")
    @NotEmpty(message = "content不能为空格！")
    private String content;

    private List<String> mkTypeNameList= Collections.emptyList();
}
