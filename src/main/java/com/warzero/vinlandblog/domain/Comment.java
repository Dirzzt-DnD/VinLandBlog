package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "评论id")
    private Long id;

    @Schema(description = "文章id")
    private String articleId;

    @Schema(description = "评论内容")
    private String content;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    @Schema(description = "是否删除标志")
    private Integer delFlag;
}
