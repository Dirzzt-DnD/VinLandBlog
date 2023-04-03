package com.warzero.vinlandblog.service.imp;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.Category;
import com.warzero.vinlandblog.domain.vo.CategoryVo;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.mapper.CategoryMapper;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.service.CategoryService;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryList() {
        return ResponseResult.okResult(categoryMapper.listCategoryCount());
    }
}
