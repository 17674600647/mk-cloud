package com.glm.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import com.glm.config.exception.MessageException;

import com.glm.entity.pojo.MkType;
import com.glm.entity.pojo.MkTypeNote;
import com.glm.mapper.MkTypeMapper;
import com.glm.mapper.MkTypeNoteMapper;
import com.glm.service.MkTypeAndNoteService;
import com.glm.utils.MkJwtUtil;
import com.glm.utils.RedisUtil;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@GlobalTransactional
public class MkTypeAndNoteServiceImpl implements MkTypeAndNoteService {
    @Autowired
    MkTypeMapper mkTypeMapper;
    @Autowired
    MkTypeNoteMapper mkTypeNoteMapper;
    @Autowired
    MkJwtUtil mkJwtUtil;
    @Autowired
    RedisUtil redisUtil;

    public static final String REDIS_MKTYPE = "mktype_";

    @Override
    public List<MkType> getAllTypeByUserId() {
        String idFromHeader = mkJwtUtil.getUserIdFromHeader();
        String redisType = (String) redisUtil.get(REDIS_MKTYPE + idFromHeader);
        if (redisType != null) {
            List<MkType> mkTypes = JSONUtil.toList(redisType, MkType.class);
            return mkTypes;
        }
        QueryWrapper<MkType> queryWrapper = new QueryWrapper<MkType>();
        queryWrapper.or().eq("user_id", Long.valueOf(idFromHeader));
        queryWrapper.or().isNull("user_id");
        queryWrapper.orderByAsc("create_time");
        List<MkType> mkTypes = mkTypeMapper.selectList(queryWrapper);
        redisUtil.cacheData(REDIS_MKTYPE + idFromHeader, mkTypes, 600L);
        return mkTypes;
    }

    @Override
    public void insertAllType(List<String> mkTypeList, Long noteId) {
        if (Objects.isNull(mkTypeList) || Objects.isNull(noteId)) {
            throw new MessageException("保存文章类型失败~");
        }
        String idFromHeader = mkJwtUtil.getUserIdFromHeader();
        List<MkType> allTypeByUserId = getAllTypeByUserId();
        Long userId = Long.valueOf(idFromHeader);
        //查询用户所有的type
        Map<String, Long> typeCollect = allTypeByUserId.stream().collect(Collectors.toMap(MkType::getName, MkType::getId));
        for (String mkType : mkTypeList) {
            Long typeId = typeCollect.get(mkType.replace(" ",""));
            //如果已经存在就添加进去
            if (!Objects.isNull(typeId)) {
                mkTypeNoteMapper.insert(new MkTypeNote(userId, noteId, typeId));
            } else {
                MkType mkType1 = new MkType(mkType, userId);
                mkTypeMapper.insert(mkType1);
                mkTypeNoteMapper.insert(new MkTypeNote(userId, noteId, mkType1.getId()));
            }
        }
        redisUtil.delete(REDIS_MKTYPE + idFromHeader);
    }

    @Override
    public IPage<MkTypeNote> getNotesIdFromTypeId(List<Long> typeId, IPage page) {
        QueryWrapper<MkTypeNote> mkTypeNoteQueryWrapper = new QueryWrapper<>();
        mkTypeNoteQueryWrapper.select("DISTINCT note_id");
        if (typeId != null && !typeId.contains(2314324231234L)) {
            mkTypeNoteQueryWrapper.in("type_id", typeId);
        }
        String uerId = mkJwtUtil.getUserIdFromHeader();
        mkTypeNoteQueryWrapper.in("user_id", List.of(Long.valueOf(uerId)));
        return mkTypeNoteMapper.selectPage(page, mkTypeNoteQueryWrapper);
    }


}
