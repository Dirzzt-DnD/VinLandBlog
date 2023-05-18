package com.warzero.vinlandblog.controller;

import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.VinlandConstant;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.dto.ArticleDto;
import com.warzero.vinlandblog.domain.dto.ArticleQueryDto;
import com.warzero.vinlandblog.domain.dto.UploadArticleDto;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = VinlandConstant.API_VERSION)
@Tag(name = "文章管理")
public class ArticleController {

    public static final String REQUSET_MODEL = "/article";

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "热门文章列表")
    @RequestMapping(value = REQUSET_MODEL + "/hotArticleList", method = RequestMethod.GET)
    public ResponseResult hotArticleList() {
        return articleService.listHotArticle();
    }

    @Operation(summary = "文章列表")
    @RequestMapping(value = REQUSET_MODEL + "/articleList", method = RequestMethod.GET)
    public ResponseResult articleList(ArticleQueryDto articleQueryDto) {
        return articleService.list(articleQueryDto);
    }

    @Operation(summary = "发布文章")
    @PostMapping(REQUSET_MODEL)
    @PreAuthorize("hasAuthority('article:add')")
    public ResponseResult addArticle(@Valid @RequestBody ArticleDto article) {
        return articleService.addArticle(article);
    }

    @PutMapping(REQUSET_MODEL)
    @PreAuthorize("hasAuthority('article:edit')")
    public ResponseResult editArticle(@Valid @RequestBody ArticleDto article) {
        return articleService.editArticle(article);
    }

    @Operation(summary = "文章详细")
    @RequestMapping(value = REQUSET_MODEL + "/{id}", method = RequestMethod.GET)
    public ResponseResult articleDetail(@PathVariable("id") Long id) {
        return articleService.getArticleDetail(id);
    }

    @Operation(summary = "文章计数")
    @RequestMapping(value = REQUSET_MODEL + "/count", method = RequestMethod.GET)
    public ResponseResult articleCount() {
        return articleService.getArticleCount();
    }

    @Operation(summary = "更新文章点击量")
    @PutMapping(REQUSET_MODEL + "/updateViewCount/{id}")
    public ResponseResult updateArticleViewCount(@PathVariable Long id) {
        return articleService.upadateViewCount(id);
    }

    @Operation(summary = "前后篇文章")
    @GetMapping(REQUSET_MODEL + "/previousNextArticle/{id}")
    public ResponseResult getPreviousNextArticle(@PathVariable Long id) {
        return articleService.getPreviousNextArticle(id);
    }

    @Operation(summary = "删除文章")
    @DeleteMapping(REQUSET_MODEL)
    public ResponseResult deleteArticle(@PathVariable Long id) {
        return articleService.deleteArticle(id);
    }

    @Operation(summary = "下载文章")
    @GetMapping(REQUSET_MODEL+"/download/{id}")
    public void downloadArticle(@PathVariable Long id, HttpServletResponse servletResponse){
        articleService.downloadArticle(id, servletResponse);
    }

    @Operation(description = "更换封面")
    @PostMapping(REQUSET_MODEL+"/replace")
    public ResponseResult replaceImage(Integer id, String url) throws IOException {
       return articleService.replaceThumbnail(id, url);
    }

    @Operation(summary = "上传文章")
    @PostMapping(REQUSET_MODEL+"/uploadArticle")
//    @PreAuthorize("hasAuthority('article:add')")
    public ResponseResult uploadArticle(UploadArticleDto uploadArticleDto) {
        return articleService.uploadArticle(uploadArticleDto);
    }
}