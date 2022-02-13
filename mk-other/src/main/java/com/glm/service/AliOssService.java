package com.glm.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @program: mk-cloud
 * @description: 阿里云文件上传oss
 * @author: lizhiyong
 * @create: 2022-02-13 14:16
 **/
public interface AliOssService {
    void createBucket();

    String upload(MultipartFile file);

    void download(String fileName) throws IOException;

    void listFile();

    void deleteFile(String fileName);
}
