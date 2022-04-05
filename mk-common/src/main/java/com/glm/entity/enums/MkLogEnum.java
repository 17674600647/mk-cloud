package com.glm.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public enum MkLogEnum {
    LOGIN("登录", 12700, LevelEnum.NORMAL.getLevel()),
    SIGN_IN("注册", 12701, LevelEnum.NORMAL.getLevel()),
    NEW_NOTE("新添加笔记", 12702, LevelEnum.NORMAL.getLevel()),
    DELETE_NOTE_SELF("用户删除笔记", 12703, LevelEnum.WARN.getLevel()),
    DELETE_NOTE_ADMIN("管理员删除笔记", 12704, LevelEnum.WARN.getLevel()),
    AUDIT_NOTE("审核笔记", 12705, LevelEnum.NORMAL.getLevel()),
    AUDIT_PASSED("审核通过",12706, LevelEnum.NORMAL.getLevel()),
    CHANGE_HEADPIC("修改头像",12707, LevelEnum.NORMAL.getLevel()),
    UPDATE_INFO("用户信息修改", 12708, LevelEnum.NORMAL.getLevel()),
    FREEZE_USER("冻结用户", 12709, LevelEnum.ERROR.getLevel()),
    BAN_USER("封禁用户", 12710, LevelEnum.ERROR.getLevel()),
    LOGIN_OUT("登出", 12711, LevelEnum.NORMAL.getLevel()),
    RECOVER_USER("恢复用户", 12712, LevelEnum.NORMAL.getLevel()),
    ;

    @Getter
    private String actionMsg;
    @Getter
    private Integer actionMark;
    @Getter
    private Integer level;
}
