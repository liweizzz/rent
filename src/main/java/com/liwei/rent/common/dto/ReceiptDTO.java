package com.liwei.rent.common.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class ReceiptDTO {
    @ExcelIgnore
    private Integer id;

    /**
     * 所属公寓ID
     */
    @ExcelIgnore
    private String apartmentId;

    /**
     * 租户ID
     */
    @ExcelIgnore
    private String tenantId;

    /**
     * 租户名
     */
    private String tenantName;

    /**
     * 房间号
     */
    @ExcelProperty(value = "房间号")
    private Integer roomNum;

    /**
     * 房租起始日期
     */
    private LocalDate rentStartDay;

    /**
     * 房租到期日期
     */
    private LocalDate rentEndDay;

    /**
     * 房租金额
     */
    private BigDecimal rentMoney;

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
    private BigDecimal elecPrice;

    /**
     * 电费
     */
    private BigDecimal elecMoney;

    /**
     * 水费
     */
    private BigDecimal waterMoney;

    /**
     * 人数
     */
    private Integer peopleCount;

    /**
     * 居住总人数
     */
    private Integer allPeopleCount;

    /**
     * 网费
     */
    private BigDecimal internetMoney;

    /**
     * 备注
     */
    private String note;

    /**
     * 收款人签名
     */
    private String signature;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 金额合计
     */
    private BigDecimal sumMoney;

    /**
     * 创建时间
     */
    @DateTimeFormat(value = "yyyy-MM-dd")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ExcelIgnore
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    @ExcelIgnore
    private Integer delFlag;

}
