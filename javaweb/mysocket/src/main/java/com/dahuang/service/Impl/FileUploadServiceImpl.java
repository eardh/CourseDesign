package com.dahuang.service.Impl;

import com.dahuang.model.json.ResponseJson;
import com.dahuang.service.FileUploadService;
import com.dahuang.utils.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    String path = ResourceUtils.getURL("classpath:").getPath()+"static/";
    String realPath = path.replace('/', '\\').substring(1,path.length());

    public FileUploadServiceImpl() throws FileNotFoundException {
    }


    /**
     *  文件上传
     * @param file
     * @param request
     * @return
     */
    @Override
    public ResponseJson upload(MultipartFile file, HttpServletRequest request) {
        // 重命名文件，防止重名
        String filename = getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        // 截取文件的后缀名
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = realPath + "upload";
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseJson().error("文件上传发生错误！");
        }
        return new ResponseJson().success()
                .setData("originalFilename", originalFilename)
                .setData("fileSize", fileSize)
                .setData("fileUrl", prefix + "\\" + filename);
    }


    /**
     *  头像上传
     * @param file
     * @param request
     * @return
     */
    @Override
    public ResponseJson avatar_upload(MultipartFile file, HttpServletRequest request) {
        // 重命名文件，防止重名
        String filename = getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        String fileSize = FileUtils.getFormatSize(file.getSize());
        // 截取文件的后缀名
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = realPath + "img\\avatar";
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseJson().error("图片上传发生错误！");
        }
        return new ResponseJson().success()
                .setData("fileUrl", "img\\avatar\\" + filename)
                .setData("fileName",filename);
    }

    private String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
