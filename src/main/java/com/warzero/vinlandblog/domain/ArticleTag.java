package com.warzero.vinlandblog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {

    private static final long serialVersionID = 1L;

    @Schema(description = "文章id")
    private Long articleId;

    @Schema(description = "标签id")
    private Long tagId;
}
