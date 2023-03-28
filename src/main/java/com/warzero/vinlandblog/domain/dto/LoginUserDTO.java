package com.warzero.vinlandblog.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginUserDTO {

    @NotBlank(message = "不能为空")
    private String userName;

    @NotBlank(message = "不能为空")
    private String password;

}
