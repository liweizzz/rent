package com.liwei.rent.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class ReceiptVO {
    private Integer id;
    //租户ID
    private String tenantId;

    //租户姓名
    private String tenantName;

    //公寓ID
    private String apartmentId;

    /**
     * 房间号
     */
    @NotBlank(message = "请选择房间号")
    private String roomNum;

    /**
     * 房租起始日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate rentStartDay;

    /**
     * 房租到期日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate rentEndDay;

    /**
     * 房租金额
     */
    @NotBlank(message = "房租金额不能为空")
    private String rentMoney;

    /**
     * 期初电表度数
     */
    private Integer curElecNum;

    /**
     * 期末电表度数
     */
    private Integer lastElecNum;

    /**
     * 电费单价
     */
    private String elecPrice;

    /**
     * 人数，用来计算总水费
     */
    private Integer peopleCount;

    /**
     * 水费
     */
    private String waterMoney;

    /**
     * 网费
     */
    private String internetMoney;

    /**
     * 押金
     */
    private String deposit;

    /**
     * 备注
     */
    private String note;

    /**
     * 收款人签名
     */
    private String signature;

    /**
     * 月份
     */
    private String month;

    /**
     * 用户姓名
     */
    private String userName;

}
