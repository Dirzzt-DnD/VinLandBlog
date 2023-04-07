package com.warzero.vinlandblog.domain.vo;

import lombok.Data;

@Data
public class CategoryCountVo {
    private Long id;
    private String name;
    private Long pid;
    private Integer count;
}
