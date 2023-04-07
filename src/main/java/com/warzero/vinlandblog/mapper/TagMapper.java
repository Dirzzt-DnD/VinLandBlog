package com.warzero.vinlandblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warzero.vinlandblog.domain.Tag;
import com.warzero.vinlandblog.domain.vo.TagCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    List<TagCountVo> listArticleTagCount();
}
