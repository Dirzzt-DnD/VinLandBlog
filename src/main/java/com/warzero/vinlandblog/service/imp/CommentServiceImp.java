package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Comment;
import com.warzero.vinlandblog.domain.vo.CommentVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.mapper.CommentMapper;
import com.warzero.vinlandblog.mapper.UserMapper;
import com.warzero.vinlandblog.service.CommentService;
import com.warzero.vinlandblog.service.UserService;
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
        commentQuery.eq(Comment::getRootId, SystemConstants.COMMENT_ROOT);
        Page<Comment> page = new Page<>();
        this.page(page,commentQuery);

        List<Comment> comments = page.getRecords();
        List<CommentVo> commentVos = getCommentVos(comments);

        commentVos.forEach(i -> i.setChildren(getChildren(i.getId())));
        return ResponseResult.okResult(new PageVo<>(page.getTotal(),commentVos));
    }


    @Override
    public ResponseResult addComment(Comment comment) {
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getToCommentId,id);
        List<Comment> comments = list(wrapper);
        return getCommentVos(comments);
    }

    private List<CommentVo> getCommentVos(List<Comment> comments) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(comments,CommentVo.class);

        for(CommentVo commentVo : commentVos){
            String nickName = userMapper.getAllById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

            if(!SystemConstants.COMMENT_ROOT.equals(commentVo.getToCommentId())){
                String name = userMapper.getAllById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(name);
            }
        }

        return commentVos;

    }

}
