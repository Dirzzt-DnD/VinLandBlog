package com.warzero.vinlandblog.service;

import com.warzero.vinlandblog.common.ResponseResult;

public interface ArchiveService {
    ResponseResult getArchiveCountList(Integer pageNum, Integer pageSize);

    ResponseResult getArchiveList(Integer pageNum, Integer pageSize);
}
