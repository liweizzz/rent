package com.liwei.rent.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoomBaseInfo {
    @ApiModelProperty(value = "房间Id")
    private Integer id;

    @ApiModelProperty(value = "房间号")
    private String roomNum;

    @ApiModelProperty(value = "备注")
    private String note;
}
