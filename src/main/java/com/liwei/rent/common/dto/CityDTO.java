package com.liwei.rent.common.dto;

import lombok.Data;

@Data
public class CityDTO {
    /**
     * 城市ID
     */
    private Integer id;

    /**
     * 城市名称
     */
    private String name;

    /**
     * 省份ID
     */
    private Integer provinceId;

    /**
     * 城市代码
     */
    private String code;

}
