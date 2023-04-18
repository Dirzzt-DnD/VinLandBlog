package com.warzero.vinlandblog.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.warzero.vinlandblog.common.ResponseResult;
import com.warzero.vinlandblog.constants.SystemConstants;
import com.warzero.vinlandblog.domain.Article;
import com.warzero.vinlandblog.domain.ArticleTag;
import com.warzero.vinlandblog.domain.Category;
import com.warzero.vinlandblog.domain.Tag;
import com.warzero.vinlandblog.domain.dto.ArticleDto;
import com.warzero.vinlandblog.domain.vo.ArticleCountVo;
import com.warzero.vinlandblog.domain.vo.ArticleDetailsVo;
import com.warzero.vinlandblog.domain.vo.ArticleListVo;
import com.warzero.vinlandblog.domain.vo.HotArticleVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.domain.vo.PreviousNextArticleVo;
import com.warzero.vinlandblog.domain.vo.TagVo;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.mapper.ArticleTagMapper;
import com.warzero.vinlandblog.mapper.CategoryMapper;
import com.warzero.vinlandblog.mapper.TagMapper;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImp extends ServiceImpl<ArticleMapper,Article> implements ArticleService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Autowired
    private ArticleMapper articleMapper;


    @Override
    public ResponseResult listHotArticle() {
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
    public ResponseResult list(Integer pageNum, Integer pageSize, Long categoryId) {

        // 构造查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getIsTop);
        wrapper.eq(categoryId != null, Article::getCategoryId, categoryId);

        // 从数据库中分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        this.page(page, wrapper);
        List<Article> articles = page.getRecords();

        List<Category> categories = categoryMapper.list(new Category());
        for (Article article : articles) {;
            String categoryName = categories.stream().filter(o -> Objects.equals(o.getId(),article.getCategoryId())).findAny().orElse(null).getName();
            article.setCategoryName(categoryName);
        }

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo<ArticleListVo>(page.getTotal(), articleListVos));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 从数据库中查询文章
        Article article = getById(id);
        ArticleDetailsVo articleDetailsVO = BeanCopyUtils.copyBean(article, ArticleDetailsVo.class);

        // 设置分类名称
        Category category = categoryMapper.selectById(article.getCategoryId());
        if (category != null) {
            articleDetailsVO.setCategoryName(category.getName());
        }

        LambdaQueryWrapper<ArticleTag> articleTagQuery = new LambdaQueryWrapper<>();
        articleTagQuery.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagQuery);
        List<Long> tagIds = articleTags.stream().map(ArticleTag::getTagId).collect(Collectors.toList());

        if(tagIds.size() > 0){
            LambdaQueryWrapper<Tag> tagQuery = new LambdaQueryWrapper<>();
            tagQuery.in(Tag::getId,tagIds);
            List<Tag> tags = tagMapper.selectList(tagQuery);
            articleDetailsVO.setTagVos(BeanCopyUtils.copyBeanList(tags, TagVo.class));
        }

        return ResponseResult.okResult(articleDetailsVO);

    }

    @Override
    public ResponseResult getArticleCount() {

        Long article = count();
        Long category = categoryMapper.selectCount(null);
        Long tag = tagMapper.selectCount(null);
        return ResponseResult.okResult(new ArticleCountVo(article,category,tag));
    }

    @Override
    public ResponseResult upadateViewCount(Long id) {
        Article article = getById(id);
        article.setViewCount(article.getViewCount()+1);
        updateById(article);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getPreviousNextArticle(Long id) {
        Article article = getById(id);
        PreviousNextArticleVo previousNextArticleVo = new PreviousNextArticleVo();


        Article previousArticle = articleMapper.getPreviousArticle(article.getCreateTime());
        if (previousArticle != null) {
            if(previousArticle.getCreateTime().compareTo(article.getCreateTime()) != 0){
                previousNextArticleVo.setPrevious(BeanCopyUtils.copyBean(previousArticle, HotArticleVo.class));
            }
        }

        Article nextArticle = articleMapper.getNextArticle(article.getCreateTime());
        if(nextArticle != null){
            if(nextArticle.getCreateTime().compareTo(article.getCreateTime())!= 0){
                previousNextArticleVo.setNext(BeanCopyUtils.copyBean(nextArticle, HotArticleVo.class));
            }
        }

        return ResponseResult.okResult(previousNextArticleVo);
    }

    @Override
    public ResponseResult addArticle(ArticleDto articleDto) {
        Article newArticle = BeanCopyUtils.copyBean(articleDto, Article.class);

        Category category = categoryMapper.getByCategoryName(articleDto.getCategory());
        if(category == null){
            category = new Category();
            category.setName(articleDto.getCategory());
            categoryMapper.insert(category);
        }


        newArticle.setCategoryId(category.getId());

        String status = articleDto.getIsDraft()? SystemConstants.ARTICLE_STATUS_DRAFT : SystemConstants.ARTICLE_STATUS_NORMAL;
        newArticle.setStatus(status);
        save(newArticle);

        List<ArticleTag> articleTags = new ArrayList<>();
        for (String name : articleDto.getTags()) {

            Tag tag = tagMapper.getByName(name);
            if(tag == null){
                tag = new Tag();
                tag.setName(name);
                tagMapper.insert(tag);
            }

            ArticleTag articleTag = new ArticleTag(newArticle.getId(), tag.getId());
            articleTags.add(articleTag);
        }
        articleTags.forEach(articleTag -> articleTagMapper.insert(articleTag));

        return ResponseResult.okResult(newArticle.getId());
    }

    @Override
    public ResponseResult editArticle(ArticleDto article) {
        LambdaQueryWrapper<ArticleTag> articleTagQuery = new LambdaQueryWrapper<>();
        articleTagQuery.eq(ArticleTag::getArticleId, article.getId());
        articleTagMapper.delete(articleTagQuery);
        return addArticle(article);
    }

}
