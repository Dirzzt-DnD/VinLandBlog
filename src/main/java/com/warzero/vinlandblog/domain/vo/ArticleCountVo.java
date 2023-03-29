package com.warzero.vinlandblog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCountVo {

    private Long article;

    private Long category;

    private Long tag;

}
