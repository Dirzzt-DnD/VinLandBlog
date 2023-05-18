package com.warzero.vinlandblog.domain.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentVo {
    private Long id;

    private Long articleId;

    private String content;

    private Long createBy;

    private Date createTime;

    private String userName;

    private String avatar;

    private Boolean isAdmin;
}
