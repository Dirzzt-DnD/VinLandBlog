package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.vo.ArchiveCountVo;
import com.warzero.vinlandblog.domain.vo.ArchiveVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.mapper.ArchiveMapper;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServiceImp implements ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult getArchiveCountList(Integer pageNum, Integer pageSize) {
        Long total = archiveMapper.selectArchiveTotalCount();
        List<ArchiveCountVo> archiveCountVos = archiveMapper.selectArchiveCount((pageNum - 1) * pageSize, pageSize);
        return ResponseResult.okResult(new PageVo<>(total,archiveCountVos));
    }

    @Override
    public ResponseResult getArchiveList(Integer pageNum, Integer pageSize) {
        List<ArchiveVo> archiveVos = archiveMapper.selectArchiveList((pageNum - 1) * pageSize, pageSize);
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        return ResponseResult.okResult(new PageVo<>(articleMapper.selectCount(wrapper), archiveVos));
    }
}
