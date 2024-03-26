package com.liwei.rent.common.dto;

import lombok.Data;

@Data
public class ApartmentDTO {
    private Integer id;

    /**
     * 房东ID
     */
    private String userId;

    /**
     * 公寓ID
     */
    private String apartmentId;

    /**
     * 公寓名称
     */
    private String apartmentName;

    /**
     * 公寓地址
     */
    private String address;

    /**
     * 删除标记 0:未删除 1:已删除
     */
    private Integer delFlag;
}
