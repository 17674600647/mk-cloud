package com.glm.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.glm.entity.MkLogs;
import com.glm.entity.NoteStatusEnum;
import com.glm.entity.ResponseResult;
import com.glm.entity.dto.GetNotesDTO;
import com.glm.entity.dto.GetOneNoteDTO;
import com.glm.entity.dto.UpdateNoteStatusDTO;
import com.glm.entity.enums.MkLogEnum;
import com.glm.entity.pojo.DataTakeOver;
import com.glm.entity.pojo.MkNotes;
import com.glm.entity.pojo.MkUrlAuth;
import com.glm.entity.vo.DataReportVO;
import com.glm.entity.vo.ObjectPageVO;
import com.glm.mapper.MkNoteMapper;
import com.glm.mapper.MkUrlAuthMapper;
import com.glm.service.AdminNoteService;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.MkKafkaUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@GlobalTransactional
public class AdminNoteServiceImpl implements AdminNoteService {
    @Autowired
    MkJwtUtil mkJwtUtil;
    @Autowired
    MkNoteMapper mkNoteMapper;
    @Autowired
    MkKafkaUtil mkKafkaUtil;
    @Autowired
    MkUrlAuthMapper mkUrlAuthMapper;


    @Override
    public ResponseResult getPageNotesByShareStatus(GetNotesDTO getNote) {
        if (getNote.getShareStatus().equals(-2)) {
            IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
            IPage<MkNotes> mkNotesIPage = mkNoteMapper.getAllDeleteNotes(notePage);
            ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
            notesPageVO.setTotal(mkNotesIPage.getTotal());
            notesPageVO.setNoteList(mkNotesIPage.getRecords());
            return ResponseResult.success("查询成功!", notesPageVO);
        }
        IPage<MkNotes> notePage = new Page<MkNotes>(getNote.getCurrentPage(), getNote.getPageSize());
        QueryWrapper<MkNotes> queryWrapper = Wrappers.<MkNotes>query()
                .orderByDesc("create_time")
                .select("id", "title", "create_time", "update_time", "classic", "user_id", "share_status");

        if (!getNote.getShareStatus().equals(3)) {
            queryWrapper.eq("share_status", getNote.getShareStatus());
        }
        IPage<MkNotes> mkNotesIPage = mkNoteMapper.selectPage(notePage, queryWrapper);
        ObjectPageVO<MkNotes> notesPageVO = new ObjectPageVO<MkNotes>();
        notesPageVO.setTotal(mkNotesIPage.getTotal());
        notesPageVO.setNoteList(mkNotesIPage.getRecords());
        return ResponseResult.success("查询成功!", notesPageVO);
    }


    @Override
    public ResponseResult adminDeleteOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.deleteById(Long.valueOf(getNote.getNoteId()));
        mkKafkaUtil.send(MkLogs.mkLogsByMkLogEnum(MkLogEnum.DELETE_NOTE_ADMIN, Long.valueOf(mkJwtUtil.getUserIdFromHeader())));
        return ResponseResult.success("删除完成！");
    }

    @Override
    public ResponseResult adminRecoverOneNote(GetOneNoteDTO getNote) {
        mkNoteMapper.recoverOneNoteById(Long.valueOf(getNote.getNoteId()));
        return ResponseResult.success("恢复成功！");
    }

    @Override
    public ResponseResult adminSetNoteStatus(UpdateNoteStatusDTO updateNoteStatus) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", Long.valueOf(updateNoteStatus.getNoteId()));
        MkNotes mkNotes = new MkNotes();
        mkNotes.setShareStatus(updateNoteStatus.getShareStatus().shortValue());
        int update = mkNoteMapper.update(mkNotes, updateWrapper);
        if (update == 1) {
            return ResponseResult.success("更新成功");
        }
        return ResponseResult.error("更新失败");
    }

    @Override
    public ResponseResult queryNoteDataReport() {
        List<DataTakeOver> dataTakeOvers = mkNoteMapper.queryDataReport();
        Map<Integer, Integer> collect = dataTakeOvers.stream().collect(Collectors.toMap(x -> x.getShareStatus(), y -> y.getTotal()));
        Double total = dataTakeOvers.stream().mapToDouble(DataTakeOver::getTotal).sum();
        DataReportVO dataReportVO = new DataReportVO();
        double notReview = 0;
        if (collect.get(NoteStatusEnum.AUDIT_PENDING.getStatus()) != null) {
            notReview = (collect.get(NoteStatusEnum.AUDIT_PENDING.getStatus()).doubleValue() / total) * 100;
        }
        double notePassed = 0;
        if (collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()) != null) {
            notePassed = (collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()).doubleValue() / total) * 100;
        }
        double notPassRate = 0;
        if (collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()) != null && collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()) != null) {
            notPassRate = (collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()).doubleValue() / (collect.get(NoteStatusEnum.AUDIT_FAIL.getStatus()).doubleValue() + collect.get(NoteStatusEnum.AUDIT_SUCCESS.getStatus()).doubleValue())) * 100;
        }
        dataReportVO.setNotReviewed((int) notReview);
        dataReportVO.setNotPassed((int) notePassed);
        dataReportVO.setNotPassedRate((int) notPassRate);
        return ResponseResult.success(dataReportVO);
    }

    @Override
    public Map<String, ArrayList<Integer>> queryUrlAuth() {
        List<MkUrlAuth> mkUrlAuths = mkUrlAuthMapper.selectList(null);
        Map<String, ArrayList<Integer>> urlMap = new HashMap<String, ArrayList<Integer>>();
        for (MkUrlAuth urlAuth : mkUrlAuths) {
            if (urlAuth.getState() == 1) {
                if (urlMap.containsKey(urlAuth.getUrl())) {
                    urlMap.get(urlAuth.getUrl()).add(urlAuth.getAuthId());
                } else {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(urlAuth.getAuthId());
                    urlMap.put(urlAuth.getUrl(),list);
                }
            }
        }
        return urlMap;
    }
}
