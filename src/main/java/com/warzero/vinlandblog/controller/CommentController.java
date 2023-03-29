package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.domain.Comment;
import com.warzero.vinlandblog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "登录处理")
public class CommentController {

    public static final String REQUEST_MODEL = "/comment";

    @Autowired
    private CommentService commentService;

    @Operation(description = "获取评论列表")
    @RequestMapping(value = REQUEST_MODEL+"/commentList",method = RequestMethod.GET)
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.getCommentList(articleId,pageNum,pageSize);
    }

    @Operation(description = "添加评论")
    @RequestMapping(value = REQUEST_MODEL,method = RequestMethod.POST)
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
