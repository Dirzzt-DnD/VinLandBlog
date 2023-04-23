package com.warzero.vinlandblog.runner;

import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RedisCommandLineRunner implements CommandLineRunner {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) {
        List<Article> articles = articleService.listViewCount();
        Map<String, Integer> viewCountMap = articles.stream()
                .collect(Collectors.toMap(
                        article -> article.getId().toString(),
                        article -> article.getViewCount().intValue()
                ));
        redisCache.setCacheMap(SystemConstants.REDIS_ARTICLE_VIEW_COUNT_KEY, viewCountMap);
    }
}
