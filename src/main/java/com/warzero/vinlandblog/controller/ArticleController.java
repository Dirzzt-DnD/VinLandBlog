package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "文章管理")
public class ArticleController {

    public static final String REQUSET_MODEL = "/article";

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "热门文章列表")
    @RequestMapping(value = REQUSET_MODEL+"/hotArticleList", method = RequestMethod.GET)
    public ResponseResult hotArticleList(){
        return articleService.hotArticle();
    }

    @Operation(summary = "文章列表")
    @RequestMapping(value = REQUSET_MODEL+"/articleList",method = RequestMethod.GET)
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }


    @Operation(summary = "文章详细")
    @RequestMapping(value = REQUSET_MODEL+"/{id}",method = RequestMethod.GET)
    public ResponseResult articleDetail(@PathVariable("id") Long id){
        return articleService.articleDetail(id);
    }

    @Operation(summary = "文章计数")
    @RequestMapping(value = REQUSET_MODEL+"/count" ,method = RequestMethod.GET)
    public ResponseResult articleCount(){
        return articleService.articleCount();
    }
}
