package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.service.ArchiveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "归档管理")
public class ArchiveController {

    public static final String REQUEST_MODEL = "/archive";

    @Autowired
    private ArchiveService archiveService;

    @RequestMapping(value = REQUEST_MODEL+"/archiveCountList",method = RequestMethod.GET)
    @Operation(description = "归档计数")
    public ResponseResult getArchiveCountList(Integer pageNum, Integer pageSize){
        return archiveService.getArchiveCountList(pageNum,pageSize);
    }

    @GetMapping(REQUEST_MODEL+"/archiveList")
    public ResponseResult getArchiveList(Integer pageNum, Integer pageSize){
        return archiveService.getArchiveList(pageNum,pageSize);
    }
}
