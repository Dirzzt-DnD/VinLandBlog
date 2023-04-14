package com.warzero.vinlandblog.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ArticleDetailsVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类名
    private String categoryName;
    //所属分类 id
    private Long categoryId;
    //缩略图
    private String thumbnail;
    //是否允许评论
    private Boolean isComment;

    private List<TagVo> tagVos;

    //访问量
    private Long viewCount;

    private Date createTime;
}
