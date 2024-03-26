package com.liwei.rent.common.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoomVO {
    @ApiModelProperty(value = "公寓ID")
    private String apartmentId;

    @ApiModelProperty(value = "公寓名称")
    private String apartmentName;

    private List<RoomBaseInfo> roomBaseInfoList;

}
