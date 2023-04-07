package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Tag;


public interface TagService extends IService<Tag> {
    ResponseResult getTagCount();
}
