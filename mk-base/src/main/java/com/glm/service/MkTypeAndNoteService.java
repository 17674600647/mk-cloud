package com.glm.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.glm.entity.pojo.MkType;
import com.glm.entity.pojo.MkTypeNote;

import java.util.List;

public interface MkTypeAndNoteService {
    public List<MkType> getAllTypeByUserId();
    public void insertAllType(List<String> mkTypeNameList,Long noteId);
    public IPage<MkTypeNote> getNotesIdFromTypeId(List<Long> typeId, IPage page);
}
