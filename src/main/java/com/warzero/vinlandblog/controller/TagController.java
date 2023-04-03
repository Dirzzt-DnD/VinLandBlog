package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "Tag功能")
public class TagController {

    private static final String REQUEST_MODEL = "/tag";

    @Autowired
    TagService tagService;

    @Operation(description = "获取评论列表")
    @RequestMapping(value = REQUEST_MODEL,method = RequestMethod.GET)
    private ResponseResult getTagCount(){
        return tagService.getTagList();
    }

}
