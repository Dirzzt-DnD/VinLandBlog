package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "分类")
@RequestMapping(value=VinlandConstant.API_VERSION)
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    public static final String REQUSET_MODEL = "/category";

    @RequestMapping( value =REQUSET_MODEL+ "/getCategoryCount", method = RequestMethod.GET)
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryCount();
    }
}
