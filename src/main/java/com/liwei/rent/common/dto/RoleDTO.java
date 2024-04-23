package com.liwei.rent.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RoleDTO {
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
