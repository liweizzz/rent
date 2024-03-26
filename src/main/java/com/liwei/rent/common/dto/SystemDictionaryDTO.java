package com.liwei.rent.common.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemDictionaryDTO {
    private Integer id;

    /**
     * key
     */
    private String itemKey;

    /**
     * value
     */
    private String itemValue;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    private Boolean delFlag;
}
