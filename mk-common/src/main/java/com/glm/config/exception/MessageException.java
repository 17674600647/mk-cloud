package com.glm.config.exception;

/**
 * @program: mk-cloud
 * @description: 常规消息错误
 * @author: lizhiyong
 * @create: 2022-03-20 11:43
 **/


public class MessageException extends RuntimeException {
    public MessageException(String msg) {
        super(msg);
    }
}
