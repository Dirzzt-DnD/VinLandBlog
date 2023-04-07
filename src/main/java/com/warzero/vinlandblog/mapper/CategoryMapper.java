package com.warzero.vinlandblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warzero.vinlandblog.domain.Category;
import com.warzero.vinlandblog.domain.vo.CategoryCountVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> list(Category category);

    List<CategoryCountVo> listCategoryCount();
}
