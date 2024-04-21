package com.liwei.rent.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Integer id;

    /**
     * 用户编号
     */
    private String userId;


    /**
     * 用户姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 角色Id
     */
    @NotBlank(message = "角色不能为空")
    private String roleId;

    /**
     * 所在省份编码
     */
    private String provinceCode;

    /**
     * 所在城市编码
     */
    private String cityCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 电话
     */
    @NotBlank(message = "电话不能为空")
    private String phone;

    /**
     * 登录密码
     */
    @NotBlank(message = "登录密码不能为空")
    private String password;

    /**
     * 登录错误次数，最高5次
     */
    private Integer loginErrorCount;

    /**
     * 状态 0：正常 1：锁定
     */
    private String status;

    /**
     * 用户类型：1：房东  2：租户
     */
    private String type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0：未删除，1：已删除
     */
    private Integer delFlag;
}
