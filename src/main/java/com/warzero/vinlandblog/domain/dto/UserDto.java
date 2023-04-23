package com.warzero.vinlandblog.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "昵称不能为空")
    private String nickName;

    @Length(max = 64,message = "个性签名的长度不能超过 64 个字符")
    private String motto;
    /**
     * 用户类型：0代表普通用户，1代表管理员
     */
    private String type;

    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    /**
     * 手机号
     */
    private String phonenumber;

    /**
     * 用户性别（0男，1女，2未知）
     */
    private String gender;

    /**
     * 头像
     */
    private String avatar;

}
