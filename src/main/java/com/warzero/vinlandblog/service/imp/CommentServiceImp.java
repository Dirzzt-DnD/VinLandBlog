package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Comment;
import com.warzero.vinlandblog.domain.User;
import com.warzero.vinlandblog.domain.vo.CommentVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.mapper.CommentMapper;
import com.warzero.vinlandblog.mapper.UserMapper;
import com.warzero.vinlandblog.service.CommentService;
import com.warzero.vinlandblog.service.UserService;
import com.warzero.vinlandblog.utils.Assert;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImp extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize){
        LambdaQueryWrapper<Comment> commentQuery = new LambdaQueryWrapper<>();
        commentQuery.eq(Comment::getArticleId,articleId);

        Page<Comment> page = new Page<>();
        this.page(page,commentQuery);

        List<Comment> comments = page.getRecords();
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments, CommentVo.class);
        for (CommentVo commentVo : commentVos) {
            User user = userMapper.getAllById(commentVo.getCreateBy());
            commentVo.setUserName(user.getNickName());
            commentVo.setIsAdmin(SystemConstants.ADMIN_USER.equals(user.getType()));
            commentVo.setAvatar(user.getAvatar());
        }
        return ResponseResult.okResult(new PageVo<>(page.getTotal(),commentVos));
    }


    @Override
    public ResponseResult addComment(Comment comment) {
        save(comment);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult updateComment(Comment comment) {

        LambdaUpdateWrapper<Comment> commentQuery = new LambdaUpdateWrapper<>();
        commentQuery.eq(Comment::getId, comment.getId());
        boolean success = update(comment,commentQuery);
        Assert.isTrue(success, AppHttpCodeEnum.RESOURCE_NOT_EXIST);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCommand(Long id) {
        boolean success = removeById(id);
        Assert.isTrue(success, AppHttpCodeEnum.RESOURCE_NOT_EXIST);
        return ResponseResult.okResult();
    }

}
