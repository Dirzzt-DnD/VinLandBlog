package com.warzero.vinlandblog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    @Schema(description = "角色权限字符串")
    private String roleKey;

    @Schema(description = "权限状态")
    private String status;

    @Schema(description = "是否删除标志")
    private Integer delFlag;


    private Long createBy;


    private Date createTime;


    private Long updateBy;


    private Date updateTime;

    /**
     * 备注
     */
    private String remark;

}
