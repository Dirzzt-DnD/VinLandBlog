package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Category implements Serializable {

    private static final long serialVersionID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "分类")
    private String name;

    @Schema(description = "父分类")
    private String pid;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "0:是,1:禁用")
    private String status;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    @Schema(description = "是否删除标志")
    private Integer delFlag;
}
