package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Tag;
import com.warzero.vinlandblog.mapper.TagMapper;
import com.warzero.vinlandblog.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class TagServiceImp extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult getTagCount() {
        return ResponseResult.okResult(tagMapper.listArticleTagCount());
    }
}
