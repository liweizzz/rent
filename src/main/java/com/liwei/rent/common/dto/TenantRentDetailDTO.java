package com.liwei.rent.common.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TenantRentDetailDTO {
    private Integer id;

    /**
     * 用户ID
     */
    private String tenantId;

    /**
     * 起租日期
     */
    private LocalDate rentStartDay;

    /**
     * 退租日期
     */
    private LocalDate rentEndDay;

    /**
     * 租房状态 0：在租 1：已退租
     */
    private Integer rentStatus;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 合同ID
     */
    private String contractId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0：未删除 1：已删除
     */
    private Integer delFlag;
}
