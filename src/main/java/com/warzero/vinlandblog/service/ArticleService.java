package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.dto.ArticleDto;
import com.warzero.vinlandblog.domain.dto.ArticleQueryDto;
import com.warzero.vinlandblog.domain.dto.UploadArticleDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService extends IService<Article> {
    ResponseResult listHotArticle();

    ResponseResult list(ArticleQueryDto articleQueryDtoDto);

    ResponseResult getArticleDetail(Long id);

    ResponseResult getArticleCount();

    List<Article> listViewCount();

    ResponseResult upadateViewCount(Long id);

    ResponseResult getPreviousNextArticle(Long id);

    ResponseResult addArticle(ArticleDto articleDto);

    ResponseResult editArticle(ArticleDto article);

    ResponseResult deleteArticle(Long id);

    void downloadArticle(Long id, HttpServletResponse servletResponse);

    ResponseResult replaceThumbnail(Integer id, String url);

    ResponseResult uploadArticle(UploadArticleDto uploadArticleDto);
}
