package com.glm.entity.vo;

import com.glm.entity.pojo.MkNotes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Collections;
import java.util.List;

/**
 * @program: mk-cloud
 * @description: 分页查询VO
 * @author: lizhiyong
 * @create: 2022-03-05 17:44
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesPageVO {
    private Long total;
    private List<MkNotes> noteList= Collections.emptyList();
}
