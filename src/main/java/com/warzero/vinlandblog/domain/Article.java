package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "文章id")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description ="文章内容")
    private String content;

    @Schema(description = "文章摘要")
    private String summary;

    @Schema(description = "分类id")
    private Long categoryId;

    @TableField(exist = false)
    @Schema(description ="分类")
    private String categoryName;

    @Schema(description = "封面图")
    private String thumbnail;

    @Schema(description = "是否置顶 0否1是")
    private String isTop;

    @Schema(description = "状态 0发布 1草稿")
    private String status;

    @Schema(description = "访问量")
    private Long viewCount;

    @Schema(description = "是否允许评论")
    private String isComment;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

    @Schema(description = "是否删除标志")
    private Integer delFlag;


    public Article(Long id, Long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }

}
