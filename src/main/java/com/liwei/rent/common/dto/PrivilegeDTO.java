package com.liwei.rent.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class PrivilegeDTO {
    private Integer id;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限码
     */
    private String code;

    /**
     * 路径
     */
    private String path;

    /**
     * 父权限ID
     */
    private Integer parent;

    /**
     * 权限类型 1：菜单，2：按钮
     */
    private String type;

    /**
     * 组件
     */
    private String component;

    /**
     * 排序，数字越小，越靠前
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标记 0：未删除，1：已删除
     */
    private Integer delFlag;

    /**
     * 子权限
     */
    private List<PrivilegeDTO> children;

}
