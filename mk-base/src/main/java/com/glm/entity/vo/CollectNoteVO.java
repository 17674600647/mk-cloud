package com.glm.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CollectNoteVO {
    public String key;
    private Double score;

    public static  List<CollectNoteVO> getCollectNoteVo(Map<String, Double> map) {
        List<CollectNoteVO> collectNoteVOList = new ArrayList<>(Collections.emptyList());
        for (String key : map.keySet()) {
            collectNoteVOList.add(new CollectNoteVO(key, map.get(key)));
        }
        return collectNoteVOList;
    }
}
