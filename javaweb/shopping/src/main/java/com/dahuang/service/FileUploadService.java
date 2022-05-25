package com.dahuang.service;

import com.dahuang.model.response.ResponseModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务接口
 * @author dahuang
 * @date 2021/6/8 11:13
 */
public interface FileUploadService {

    /**
     * 图片上传接口
     * @author dahuang
     * @date 2021/6/8 9:37
     * @param file
     * @return com.dahuang.model.response.ResponseModel
     */
    ResponseModel upload(MultipartFile file);

}
