package com.glm.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @program: mk-cloud
 * @description: ceshi1
 * @author: lizhiyong
 * @create: 2022-02-15 15:09
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Accessors(chain = true)//链式编程
public class TestException extends RuntimeException {
    public TestException(String msg) {
        super(msg);
    }
}
