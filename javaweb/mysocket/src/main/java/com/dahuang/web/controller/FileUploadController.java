package com.dahuang.web.controller;

import com.dahuang.mapper.UserMapper;
import com.dahuang.model.json.ResponseJson;
import com.dahuang.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/chatroom")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping(value = "/upload", method = POST)
    @ResponseBody
    public ResponseJson upload(
            @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) {
        return fileUploadService.upload(file, request);
    }

    @RequestMapping(value = "/avatar_upload", method = POST)
    @ResponseBody
    public ResponseJson avatar_upload(
            @RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) {
        return fileUploadService.avatar_upload(file, request);
    }
}
