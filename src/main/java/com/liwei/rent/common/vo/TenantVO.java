package com.liwei.rent.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author liwei
 * @since 2024-01-21
 */
@Data
public class TenantVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键Id")
    private Integer id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

    @ApiModelProperty(value = "房东Id")
    private String userId;

    @ApiModelProperty(value = "房东名称")
    private String userName;

    @ApiModelProperty(value = "户籍地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "租户状态 0：在租，1：退租")
    private String status;

    @ApiModelProperty(value = "所属公寓ID")
    private String apartmentId;

}
