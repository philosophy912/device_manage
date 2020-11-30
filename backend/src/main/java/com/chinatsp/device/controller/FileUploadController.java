package com.chinatsp.device.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@RestController
@Slf4j
public class FileUploadController {

    /**
     * 文件名长度
     */
    private static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_EXTENSIONS = {
            "jpg", "img", "png", "gif"
    };

    /**
     * 项目路径
     */
    private static final String UPLOADED_FOLDER = System.getProperty("user.dir");

    @PostMapping("/upload")
    public Map<String, Object> singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            throw new Exception("文件为空!");
        }
        String filename = upload(file);
        String url = "/upload/" + filename;
        Map<String, Object> map = new HashMap<>(2);
        map.put("msg", "上传成功");
        map.put("url", url);
        return map;
    }


    /**
     * 上传方法
     */
    private String upload(MultipartFile file) throws Exception {
        int len = Objects.requireNonNull(file.getOriginalFilename()).length();
        if (len > DEFAULT_FILE_NAME_LENGTH) {
            throw new Exception("文件名超出限制!");
        }
        String extension = getExtension(file);
        if (!isValidExtension(extension)) {
            throw new Exception("文件格式不正确");
        }
        // 自定义文件名
        String filename = getPathName(file);
        // 获取file对象
        File desc = getFile(filename);
        // 写入file
        file.transferTo(desc);
        return filename;
    }

    /**
     * 获取file对象
     */
    @SneakyThrows
    private File getFile(String filename) {
        File file = new File(UPLOADED_FOLDER + "/" + filename);
        if (!file.getParentFile().exists()) {
            boolean result = file.getParentFile().mkdirs();
            if (!result) {
                log.debug("mkdirs failed");
            }
        }
        if (!file.exists()) {
            boolean result = file.createNewFile();
            if (!result) {
                log.debug("createNewFile failed");
            }
        }
        return file;
    }

    /**
     * 验证文件类型是否正确
     */
    private boolean isValidExtension(String extension) {
        for (String allowedExtension : ALLOWED_EXTENSIONS) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 此处自定义文件名,uuid + extension
     */
    private String getPathName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID().toString() + "." + extension;
    }

    /**
     * 获取扩展名
     */
    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null) {
            return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        throw new RuntimeException("get extension fail");
    }
}
