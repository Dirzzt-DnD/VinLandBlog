package com.warzero.vinlandblog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "评论id")
    private Long id;

    @Schema(description = "评论类型")
    private String type;

    @Schema(description = "文章id")
    private String articleId;

    @Schema(description = "根评论id")
    private String rootId;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "被回复的用户id")
    private Long toCommentUserId;

    @Schema(description = "被回复的id")
    private Long toCommentId;


    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    @Schema(description = "是否删除标志")
    private Integer delFlag;
}
