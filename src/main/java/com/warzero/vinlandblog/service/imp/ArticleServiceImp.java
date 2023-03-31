package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.Category;
import com.warzero.vinlandblog.domain.vo.ArticleCountVo;
import com.warzero.vinlandblog.domain.vo.ArticleDetailsVo;
import com.warzero.vinlandblog.domain.vo.ArticleListVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.mapper.CategoryMapper;
import com.warzero.vinlandblog.mapper.TagMapper;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ArticleServiceImp extends ServiceImpl<ArticleMapper,Article> implements ArticleService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult hotArticle() {
        // 查询出非草稿、没有被删除的文章，并按照热度降序排序前 10 文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getViewCount);
        // wrapper.last("limit 10");

        Page<Article> page = new Page<>(1, 5);
        this.page(page, wrapper);

        List<Article> records = page.getRecords();
        return ResponseResult.okResult(records);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {

        // 构造查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getIsTop);
        wrapper.eq(categoryId != null, Article::getCategoryId, categoryId);

        // 从数据库中分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        List<Article> articles = page.getRecords();

        // 设置文章分类名
//        List<Category> categories = categoryMapper.list(new Category());
//        for (Article article : articles) {;
//            String categoryName = categories.stream().filter(o -> Objects.equals(o.getId(),article.getCategoryId())).findAny().orElse(null).getName();
//            article.setCategoryName(categoryName);
//        }

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo<ArticleListVo>(page.getTotal(), articleListVos));
    }

    @Override
    public ResponseResult articleDetail(Long id) {
        // 从数据库中查询文章
        Article article = getById(id);
        ArticleDetailsVo articleDetailsVO = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);

        // 设置分类名称
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            articleDetailsVO.setCategoryName(category.getName());
        }

        return ResponseResult.okResult(articleDetailsVO);

    }

    @Override
    public ResponseResult articleCount() {

        Long article = count();
        Long category = categoryMapper.selectCount(null);
        Long tag = tagMapper.selectCount(null);
        return ResponseResult.okResult(new ArticleCountVo(article,category,tag));
    }

}
