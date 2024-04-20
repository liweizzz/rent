package com.liwei.rent.common.dto;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RoleDTO {
    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

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
