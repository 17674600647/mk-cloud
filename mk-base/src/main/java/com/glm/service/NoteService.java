package com.glm.service;

import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.NoteDTO;
import com.glm.entity.dto.SearchNotesDTO;

/**
 * @program: mk-cloud
 * @description: 笔记服务层
 * @author: lizhiyong
 * @create: 2022-02-15 14:32
 **/
public interface NoteService {
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++增++++++++++++++++++++++++++++++++++++++*/
    /**
     * 保存文章
     * @param noteDTO
     * @return
     */
    public ResponseResult saveNote(NoteDTO noteDTO);
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++删++++++++++++++++++++++++++++++++++++++*/
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++查++++++++++++++++++++++++++++++++++++++*/

    /**
     * 分页获取文章列表
     * @param getNote
     * @return
     */
    public ResponseResult getPageNotes(GetNotesDTO getNote );



    /**
     * 获取被删除的文章列表
     * @param getNote
     * @return
     */
    public ResponseResult getPageDeleteNotes(GetNotesDTO getNote );

    /**
     * 获取一个文章
     * @param getNote
     * @return
     */
    public ResponseResult getOneNotes(GetOneNoteDTO getNote );

    /**
     * 获取文章的RPC接口
     * @param getNote
     * @return
     */
    public ResponseResult getOneNotesRpc(GetOneNoteDTO getNote );

    /**
     * 获取分享的文章
     * @param getNote
     * @return
     */
    public ResponseResult getSharedNotesApi(GetNotesDTO getNote );
    /*+++++++++++++++++++++++++++++++++++++++++++++++++++++改++++++++++++++++++++++++++++++++++++++*/

    /**
     * 删除一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult deleteOneNote(GetOneNoteDTO getNote );

    /**
     * 恢复一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult recoverOneNote(GetOneNoteDTO getNote );

    /**
     * 分享一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult toShareNote(GetOneNoteDTO getNote );

    /**
     * 收藏一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult toCollectNote(GetOneNoteDTO getNote );
    /**
     * 取消收藏一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult toDisCollect(GetOneNoteDTO getNote );
    /**
     * 取消分享一篇文章
     * @param getNote
     * @return
     */
    public ResponseResult toDishareNote(GetOneNoteDTO getNote );
    /**
     * 查询收藏的文章
     * @param getNote
     * @return
     */
    public ResponseResult queryCollectNotes(GetNotesDTO getNote );
}
