package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Comment;

public interface CommentService extends IService<Comment> {

    ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);

    ResponseResult updateComment(Comment comment);

    ResponseResult deleteCommand(Long id);
}
