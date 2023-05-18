package com.warzero.vinlandblog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.warzero.vinlandblog.domain.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> list(Article article);

    Article getPreviousArticle(Date createTime);

    Article getNextArticle(Date createTime);

    int updateImage(Integer id, String urlThumbnail);
}
