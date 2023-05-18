package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Api(tags = "图片上传")
public class UploadImageController {

    public static final String REQUSET_MODEL = "/image";


    @Value("${blog.uploadImgPath}")
    private String imgUploadPath;

    @Value("${blog.host}")
    private String host;

    @Value("${blog.access-img-url}")
    private String imgAccessURL;

    @Autowired
    private ArticleMapper articleMapper;

    @PostMapping(REQUSET_MODEL+"/upload")
    @Operation(description = "上传图片")
    public ResponseResult uploadImg(@RequestParam(value = "file") MultipartFile file) throws IOException {

        String imgName = UUID.randomUUID().toString();
        imgName+=".png";
        String path = imgUploadPath+imgName;
        String url = host+imgAccessURL+imgName;

        File fileLocal = new File(path);
        file.transferTo(fileLocal);
        return fileLocal.exists()? ResponseResult.okResult(url) : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }
}
