package com.warzero.vinlandblog.service.imp;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.vo.ArchiveCountVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.mapper.ArchiveMapper;
import com.warzero.vinlandblog.service.ArchiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServiceImp implements ArchiveService {

    @Autowired
    private ArchiveMapper archiveMapper;

    @Override
    public ResponseResult getArchiveCountList(Integer pageNum, Integer pageSize) {
        Long total = archiveMapper.selectArchiveTotalCount();
        List<ArchiveCountVo> archiveCountVos = archiveMapper.selectArchiveCount((pageNum - 1) * pageSize, pageSize);
        return ResponseResult.okResult(new PageVo<>(total,archiveCountVos));
    }
}
