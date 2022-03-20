package com.glm.entity.vo;

import com.glm.entity.pojo.MkNotes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * @program: mk-cloud
 * @description:
 * @author: lizhiyong
 * @create: 2022-03-20 10:45
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ObjectPageVO<T> {
    private Long total;
    private List<T> noteList = Collections.emptyList();
}
