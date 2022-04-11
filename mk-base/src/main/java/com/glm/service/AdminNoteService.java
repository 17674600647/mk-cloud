package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.UpdateNoteStatusDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface AdminNoteService {
    /**
     * 根据分享状态获取文章列表
     * @param getNote
     * @return
     */
    public ResponseResult getPageNotesByShareStatus(GetNotesDTO getNote );

    /**
     * 管理员删除一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult adminDeleteOneNote(GetOneNoteDTO getNote );
    /**
     * 管理员恢复一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult adminRecoverOneNote(GetOneNoteDTO getNote );

    /**
     * 管理员恢复一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult adminSetNoteStatus(UpdateNoteStatusDTO getNote );
    /**
     * 查询文章数据
     * @return
     */
    public ResponseResult queryNoteDataReport();
    /**
     * 查询接口权限数据
     * @return
     */
    public Map<String, ArrayList<Integer>> queryUrlAuth();
}
