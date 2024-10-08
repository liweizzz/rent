package com.liwei.rent.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomDTO {
    private Integer id;

    /**
     * 公寓ID
     */
    private String apartmentId;

    /**
     * 公寓名称
     */
    private String apartmentName;

    /**
     * 房间号
     */
    private String roomNum;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0：未删除，1：已删除
     */
    private Integer delFlag;

}
