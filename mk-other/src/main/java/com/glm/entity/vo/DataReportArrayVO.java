package com.glm.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DataReportArrayVO {
    public String[] days;
    public Integer[] userNumbChange;
    public Integer[] noteNumbChange;
    public Integer[] loginNumbChange;
    public Integer[] shareNumbChange;

    public DataReportArrayVO(DataReportVo dataReportVo) {
        days = dataReportVo.getDays().toArray(new String[dataReportVo.getDays().size()]);
        userNumbChange = dataReportVo.getUserNumbChange().toArray(new Integer[dataReportVo.getUserNumbChange().size()]);
        noteNumbChange = dataReportVo.getNoteNumbChange().toArray(new Integer[dataReportVo.getNoteNumbChange().size()]);
        loginNumbChange = dataReportVo.getLoginNumbChange().toArray(new Integer[dataReportVo.getLoginNumbChange().size()]);
        shareNumbChange = dataReportVo.getShareNumbChange().toArray(new Integer[dataReportVo.getShareNumbChange().size()]);
    }
}
