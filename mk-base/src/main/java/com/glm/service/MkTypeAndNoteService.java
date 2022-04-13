package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.pojo.MkType;

import java.util.List;

public interface MkTypeAndNoteService {
    public List<MkType> getAllTypeByUserId();
    public void insertAllType(List<String> mkTypeNameList,Long noteId);
}
