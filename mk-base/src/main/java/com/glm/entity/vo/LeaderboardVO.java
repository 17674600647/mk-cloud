package com.glm.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.glm.entity.pojo.MkNotes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LeaderboardVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String title;
    private Double hotScore;

    public static List<LeaderboardVO> voFromkNotes(List<MkNotes> mkNotesList, Map<String, Double> stringDoubleMap) {
        List<LeaderboardVO> leaderboardVOSList = new ArrayList<>();
        for (MkNotes mkNotes : mkNotesList) {
            leaderboardVOSList.add(new LeaderboardVO(mkNotes.getId(), mkNotes.getTitle(), stringDoubleMap.get(String.valueOf(mkNotes.getId()))));
        }
        return leaderboardVOSList;
    }
}
