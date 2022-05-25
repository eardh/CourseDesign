package com.dahuang.service.Impl;

import com.dahuang.model.enums.ResponseCode;
import com.dahuang.model.response.ResponseModel;
import com.dahuang.service.FileUploadService;
import com.dahuang.until.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${FileService.imagePath}")
    private String imagePath;

    String path = ResourceUtils.getURL("classpath:").getPath()+ "FileService/";
    String realPath = path.replace('/', '\\').substring(1,path.length());

    public FileUploadServiceImpl() throws FileNotFoundException {

    }

    @Override
    public ResponseModel upload(MultipartFile file) {

        logger.info("文件传输");

        // 重命名文件，防止重名
        String filename = UuidUtils.getRandomUUID();
        String suffix = "";
        String originalFilename = file.getOriginalFilename();

        // 截取文件的后缀名
        if (originalFilename.contains(".")) {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        filename = filename + suffix;
        String prefix = realPath + "\\images";
        logger.info(filename);
        Path filePath = Paths.get(prefix, filename);
        try {
            Files.copy(file.getInputStream(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("文件传输异常");
            return ResponseModel.fail(ResponseCode.FAILURE).setMessage("图片上传错误");
        }

        String file_path = imagePath + filename;

        logger.info("图片访问路径：" + file_path);
        logger.info("图片真实路径：" + filePath);

        return ResponseModel.success(file_path).setMessage("图片上传成功");

    }
}
