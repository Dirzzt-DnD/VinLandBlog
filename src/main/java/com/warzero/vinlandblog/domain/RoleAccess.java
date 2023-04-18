package com.warzero.vinlandblog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleAccess implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long roleId;

    private Long accessId;

}
