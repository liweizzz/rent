package com.liwei.rent.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@Data
public class TenantDTO implements Serializable {

    @ApiModelProperty(value = "主键ID")
    private Integer id;

    @ApiModelProperty(value = "租户ID")
    private Integer tenantId;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

    @ApiModelProperty(value = "所属房东ID")
    private Integer userId;

    @ApiModelProperty(value = "所属房东名称")
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

    @ApiModelProperty(value = "所属公寓名称")
    private String apartmentCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记 0：未删除，1：已删除")
    private Integer delFlag;


}
