package com.dahuang.service;

import com.dahuang.model.json.ResponseJson;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface FileUploadService {

    ResponseJson upload(MultipartFile file, HttpServletRequest request);

    ResponseJson avatar_upload(MultipartFile file, HttpServletRequest request);
}
