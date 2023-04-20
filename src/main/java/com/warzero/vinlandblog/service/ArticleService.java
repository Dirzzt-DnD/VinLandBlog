package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.dto.ArticleDto;

public interface ArticleService extends IService<Article> {
    ResponseResult listHotArticle();

    ResponseResult list(Integer pageNum, Integer pageSize, Long categoryId, Long tagId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult getArticleCount();

    ResponseResult upadateViewCount(Long id);

    ResponseResult getPreviousNextArticle(Long id);

    ResponseResult addArticle(ArticleDto articleDto);

    ResponseResult editArticle(ArticleDto article);
}
