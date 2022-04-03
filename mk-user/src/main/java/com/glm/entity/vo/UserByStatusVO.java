package com.glm.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.glm.entity.pojo.MkUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserByStatusVO {
    private String id;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String email;
    private Integer age;
    private String describe;
    private String nickName;
    private Short status;

    public UserByStatusVO convertByMkUser(MkUser mkUser){
        BeanUtils.copyProperties(mkUser,this);
        this.id=String.valueOf(mkUser.getId());
        return this;
    }

}
