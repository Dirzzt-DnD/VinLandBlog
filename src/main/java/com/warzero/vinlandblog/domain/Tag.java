package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

    @Schema(description = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;
}
