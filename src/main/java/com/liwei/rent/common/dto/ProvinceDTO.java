package com.liwei.rent.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProvinceDTO {
    private Integer id;
    private String name;
    private String code;
    private List<CityDTO> cityList;
}
