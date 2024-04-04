package com.liwei.rent.common.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 角色Id
     */
    private List<Integer> roleIds;

    /**
     * 所在省份编码
     */
    private String provinceId;

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
    private String phone;

    /**
     * 登录密码
     */
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
