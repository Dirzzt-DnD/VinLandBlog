package com.warzero.vinlandblog.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String category;

    private String content;

    private String summary;

    private List<String> tags;

    private String thumbnail;

    private Boolean isDraft;

}
