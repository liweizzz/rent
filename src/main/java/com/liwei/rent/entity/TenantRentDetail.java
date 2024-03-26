package com.liwei.rent.entity;

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
 * 用户租房明细表
 * </p>
 *
 * @author liwei
 * @since 2024-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tenant_rent_detail")
public class TenantRentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID
     */
    private String tenantId;

    /**
     * 起租日期
     */
    private LocalDate rentStartDay;

    /**
     * 退租日期
     */
    private LocalDate rentEndDay;

    /**
     * 租房状态 0：在租 1：已退租
     */
    private Integer rentStatus;

    /**
     * 房间号
     */
    private Integer roomNum;

    /**
     * 合同ID
     */
    private Integer contractId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 删除标记 0：未删除 1：已删除
     */
    private Integer delFlag;


}
