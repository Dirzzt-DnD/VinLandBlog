package com.warzero.vinlandblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warzero.vinlandblog.domain.Link;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LinkMapper extends BaseMapper<Link> {
}
