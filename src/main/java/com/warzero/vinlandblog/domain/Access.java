package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("access")
public class Access implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    @Schema(description = "权限名")
    private String accessName;

    @Schema(description = "权限标识")
    private String permission;

    @Schema(description = "权限状态")
    private String status;

    private Long createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    private Long updateBy;

    @TableField(fill = FieldFill.INSERT)
    private Date updateTime;

    @Schema(description = "是否删除标志")
    private Integer delFlag;

    @Schema(description = "备注")
    private String remark;
}
