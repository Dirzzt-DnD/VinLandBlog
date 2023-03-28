package com.warzero.vinlandblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.domain.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticle();

    ResponseResult articleList(Integer pageNum,Integer pageSize, Long categoryId);

    ResponseResult articleDetail(Long id);
}
