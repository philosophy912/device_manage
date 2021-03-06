package com.chinatsp.device.controller;

import com.chinatsp.device.entity.vo.Response;
import com.chinatsp.device.utils.Constant;
import com.philosophy.base.util.FilesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@RestController
@Slf4j
@Api(value = "文件上传接口", tags = {"文件上传"})
public class FileUploadController {

    @Value(("${upload_file}"))
    private String upload_images;


    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_EXTENSIONS = {
            "jpg", "png", "gif", "bmp"
    };


    @SneakyThrows
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件")
    public Response singleFileUpload(@RequestParam("file") MultipartFile file) {
        Response response = new Response();
        try{
            String originFileName = file.getOriginalFilename();
            log.debug("origin name is {}", originFileName);
            if (originFileName != null) {
                String extendName = FilesUtils.getExtension(Paths.get(originFileName));
                log.debug("extend name is {}", extendName);
                boolean flag = false;
                for (String ext : ALLOWED_EXTENSIONS) {
                    if (ext.equalsIgnoreCase(extendName)) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    response.setCode(Constant.NOK);
                    response.setMessage("文件扩展名必须是" + Arrays.toString(ALLOWED_EXTENSIONS));
                } else {
                    String fileName = System.currentTimeMillis() + "." + extendName;
                    Path serverFile = Paths.get(upload_images, fileName);
                    if (!Files.exists(Paths.get(upload_images))) {
                        Files.createDirectories(Paths.get(upload_images));
                    }
                    try {
                        file.transferTo(serverFile);
                        response.setData("/images/" + serverFile.getFileName().toString());
                        response.setMessage("upload file success");
                    } catch (IOException | IllegalStateException e) {
                        response.setCode(Constant.SERVER_ERROR);
                        response.setMessage(e.getMessage());
                    }
                }
            } else {
                response.setCode(Constant.NOK);
                response.setMessage("文件不正确");
            }
        }catch (Exception e){
            response.setCode(Constant.NOK);
            response.setMessage("文件不正确");
            response.setErrorInfo(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }
}
