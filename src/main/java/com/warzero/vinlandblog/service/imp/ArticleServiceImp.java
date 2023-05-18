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
import com.warzero.vinlandblog.domain.dto.ArticleQueryDto;
import com.warzero.vinlandblog.domain.dto.UploadArticleDto;
import com.warzero.vinlandblog.domain.vo.ArticleCountVo;
import com.warzero.vinlandblog.domain.vo.ArticleDetailsVo;
import com.warzero.vinlandblog.domain.vo.ArticleListVo;
import com.warzero.vinlandblog.domain.vo.HotArticleVo;
import com.warzero.vinlandblog.domain.vo.PageVo;
import com.warzero.vinlandblog.domain.vo.PreviousNextArticleVo;
import com.warzero.vinlandblog.domain.vo.TagVo;
import com.warzero.vinlandblog.enums.AppHttpCodeEnum;
import com.warzero.vinlandblog.excepetion.SystemException;
import com.warzero.vinlandblog.mapper.ArticleMapper;
import com.warzero.vinlandblog.mapper.ArticleTagMapper;
import com.warzero.vinlandblog.mapper.CategoryMapper;
import com.warzero.vinlandblog.mapper.TagMapper;
import com.warzero.vinlandblog.service.ArticleService;
import com.warzero.vinlandblog.utils.Assert;
import com.warzero.vinlandblog.utils.BeanCopyUtils;
import com.warzero.vinlandblog.utils.DateUtils;
import com.warzero.vinlandblog.utils.RedisCache;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult listHotArticle() {
        // 查询出非草稿、没有被删除的文章，并按照热度降序排序前 5 文章
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getViewCount);
        // wrapper.last("limit 5");

        Page<Article> page = new Page<>(1, 5);
        this.page(page, wrapper);

        List<Article> records = page.getRecords();
        return ResponseResult.okResult(records);
    }


    @Override
    public ResponseResult list(ArticleQueryDto articleQueryDto) {

        // 构造查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.orderByDesc(Article::getIsTop).orderByDesc(Article::getCreateTime);
        wrapper.eq(articleQueryDto.getCategoryId() != null, Article::getCategoryId, articleQueryDto.getCategoryId());

        if (articleQueryDto.getTagId() != null) {
            LambdaQueryWrapper<ArticleTag> tagWrapper = new LambdaQueryWrapper<>();
            tagWrapper.eq(ArticleTag::getTagId, articleQueryDto.getTagId());
            List<ArticleTag> articleTags = articleTagMapper.selectList(tagWrapper);
            if (!articleTags.isEmpty()){
                wrapper.in(Article::getId, articleTags.stream().map(ArticleTag::getArticleId).collect(Collectors.toList()));
            }
        }

        if (articleQueryDto.getDate() != null) {
            try {
                Map<String, Date> dateRange = DateUtils.getDateRange(articleQueryDto.getDate());
                wrapper.between(Article::getCreateTime, dateRange.get("start"), dateRange.get("end"));
            } catch (ParseException e) {
                throw new SystemException(AppHttpCodeEnum.DATE_NOT_VALID);
            }
        }

        // 从数据库中分页查询
        Page<Article> page = new Page<>(articleQueryDto.getPageNum(), articleQueryDto.getPageSize());
        this.page(page, wrapper);
        List<Article> articles = page.getRecords();

        List<Category> categories = categoryMapper.list(new Category());
        for (Article article : articles) {
            String categoryName = categories.stream().filter(o -> Objects.equals(o.getId(),article.getCategoryId())).findAny().orElse(null).getName();
            article.setCategoryName(categoryName);
        }


        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);
        return ResponseResult.okResult(new PageVo<>(page.getTotal(), articleListVos));
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        // 从数据库中查询文章
        Article article = getById(id);
        Assert.notNull(article, AppHttpCodeEnum.PARAM_NOT_VALID);
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
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        Long article = articleMapper.selectCount(wrapper);
        Long category = categoryMapper.selectCount(null);
        Long tag = tagMapper.selectCount(null);
        return ResponseResult.okResult(new ArticleCountVo(article,category,tag));
    }

    @Override
    public List<Article> listViewCount() {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getViewCount);
        return list(queryWrapper);
    }

    @Override
    public ResponseResult upadateViewCount(Long id) {
        redisCache.increaseCacheMapValue(SystemConstants.REDIS_ARTICLE_VIEW_COUNT_KEY, id.toString(), 1);
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
        Article article = bindTagAndCategory(articleDto);
        return ResponseResult.okResult(article.getId());
    }

    @Override
    public ResponseResult editArticle(ArticleDto article) {
        LambdaQueryWrapper<ArticleTag> articleTagQuery = new LambdaQueryWrapper<>();
        articleTagQuery.eq(ArticleTag::getArticleId, article.getId());
        articleTagMapper.delete(articleTagQuery);
        return addArticle(article);
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        LambdaQueryWrapper<Article> articleQuery = new LambdaQueryWrapper<>();
        articleQuery.eq(Article::getId, id);
        return articleMapper.delete(articleQuery) > 1? ResponseResult.okResult() : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public void downloadArticle(Long id, HttpServletResponse servletResponse) {
        Article article = articleMapper.selectById(id);
        String articleContent = article.getContent();
        byte[]contentByte = articleContent.getBytes(StandardCharsets.UTF_8);

        InputStream inputStream =new ByteArrayInputStream(contentByte);
        try {
            servletResponse.setContentType("application/octet-stream");
            servletResponse.setCharacterEncoding("utf-8");
            servletResponse.setHeader("content-disposition", "attachement;filename="+java.net.URLEncoder.encode(article.getTitle()+".md", "UTF-8"));
            servletResponse.setHeader("Connection", "close");
            //设置传输的类型
            servletResponse.setHeader("Content-Transfer-Encoding", "chunked");
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
            OutputStream outputStream = servletResponse.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while((len = inputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseResult replaceThumbnail(Integer id, String url) {
        int result = articleMapper.updateImage(id,url);
        return result > 0? ResponseResult.okResult(url) : ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public ResponseResult uploadArticle(UploadArticleDto uploadArticleDto) {
       Article uploadArticle = bindTagAndCategory(BeanCopyUtils.copyBean(uploadArticleDto, ArticleDto.class));
       String fileName = uploadArticleDto.getMultipartFile().getOriginalFilename();
       String suffix = fileName.substring(fileName.lastIndexOf("."));
       String content;
        try {
            File file = File.createTempFile(fileName,suffix);
            uploadArticleDto.getMultipartFile().transferTo(file);
            byte[] contentByte = Files.readAllBytes(file.toPath());
            content = new String(contentByte, StandardCharsets.UTF_8);
            uploadArticle.setContent(content);
            articleMapper.updateById(uploadArticle);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
       return ResponseResult.okResult(content);
    }


    private Article bindTagAndCategory(ArticleDto articleDto){
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
        saveOrUpdate(newArticle);

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
        if(articleTags!=null){
            articleTags.forEach(articleTag -> articleTagMapper.insert(articleTag));
        }

        return newArticle;
    }



}
