package com.warzero.vinlandblog.schedule;

import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RedisUpdateViewCount {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void updateViewCount() {
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDIS_ARTICLE_VIEW_COUNT_KEY);
        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());

        articleService.updateBatchById(articles);
    }
}
