package com.liwei.rent.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 收据表
 * </p>
 *
 * @author liwei
 * @since 2024-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("receipt")
public class Receipt implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 所属公寓ID
     */
    private String apartmentId;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 租户姓名
     */
    private String tenantName;

    /**
     * 房间号
     */
    private String roomNum;

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
     * 人数，用来统计总水费
     */
    private Boolean peopleCount;

    /**
     * 水费
     */
    private BigDecimal waterMoney;

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
     * 金额合计
     */
    private BigDecimal sumMoney;

    /**
     * 押金
     */
    private BigDecimal deposit;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记
     */
    private Integer delFlag;


}
