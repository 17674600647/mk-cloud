package com.glm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public enum NoteStatusEnum {
    AUDIT_PENDING(0,"待审核"),
    AUDIT_FAIL(2,"审核未通过"),
    AUDIT_SUCCESS(1,"审核通过，已分享的"),
    AUDIT_NEVER(-1,"未发起审核的"),
    ;
    @Getter
    private Integer status;
    @Getter
    private String msg;
}
