package com.liwei.rent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tenant")
@ApiModel(value="Tenant对象", description="租户表")
public class Tenant implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "租户名")
    private String tenantName;

    @ApiModelProperty(value = "所属房东ID")
    private String userId;

    @ApiModelProperty(value = "所属房东名称")
    private String userName;

    @ApiModelProperty(value = "户籍地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "身份证号码")
    private String idCard;

    @ApiModelProperty(value = "所属公寓ID")
    private String apartmentId;

    @ApiModelProperty(value = "所属公寓名称")
    private String apartmentName;

    @ApiModelProperty(value = "租户状态 0：在租，1：退租")
    private String status;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "删除标记 0：未删除，1：已删除")
    private Integer delFlag;

}
