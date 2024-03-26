package com.liwei.rent.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoomDTO {
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "房间号")
    private String roomNum;

}
