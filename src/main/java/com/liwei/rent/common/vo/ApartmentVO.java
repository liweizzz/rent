package com.liwei.rent.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ApartmentVO {
    private Integer id;
    private String userId;
    @NotBlank(message = "请输入公寓名称")
    private String apartmentName;
    @NotBlank(message = "请输入地址")
    private String address;
}
