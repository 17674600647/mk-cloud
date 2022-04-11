package com.glm.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DataReportVo {
    public List<String> days;
    public List<Integer> userNumbChange;
    public List<Integer> noteNumbChange;
    public List<Integer> loginNumbChange;
    public List<Integer> shareNumbChange;
    public DataReportVo() {
        this.days=new ArrayList<String>();
        this.userNumbChange=new ArrayList<Integer>();
        this.noteNumbChange=new ArrayList<Integer>();
        this.loginNumbChange=new ArrayList<Integer>();
        this.shareNumbChange=new ArrayList<Integer>();
    }
}
