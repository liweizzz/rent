package com.liwei.rent.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TenantRentDetailVO {
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "起租日期")
    private LocalDate rentStartDay;

    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "退租日期")
    private LocalDate rentEndDay;

    @ApiModelProperty(value = "租房状态 0：在租 1：已退租")
    private Integer rentStatus;

    @ApiModelProperty(value = "房间号")
    private String roomNum;

    @ApiModelProperty(value = "合同ID")
    private String contractId;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标记 0：未删除 1：已删除")
    private Integer delFlag;
}
