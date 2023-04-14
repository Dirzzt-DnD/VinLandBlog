package com.warzero.vinlandblog.domain.vo;

import lombok.Data;

@Data
public class UserInfoVo {
    private Long id;

    private String nickName;

    private String motto;

    private String avatar;

    private String gender;

    private Boolean isAdmin;

    private String email;
}
