package com.liwei.rent.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginVO {
    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
}
