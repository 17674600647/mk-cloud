package com.glm.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：lzy
 * @description：TODO
 * @date ：2021/12/16 13:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginVO {
    private String token;
    private String authInfo;
}
