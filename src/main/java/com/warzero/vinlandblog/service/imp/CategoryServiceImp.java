package com.warzero.vinlandblog.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Category;
import com.warzero.vinlandblog.mapper.CategoryMapper;
import com.warzero.vinlandblog.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryServiceImp extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ResponseResult getCategoryCount() {
        return ResponseResult.okResult(categoryMapper.listCategoryCount());
    }
}
