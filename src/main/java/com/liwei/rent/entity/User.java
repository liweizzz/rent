package com.liwei.rent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户(房东)表
 * </p>
 *
 * @author liwei
 * @since 2024-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 角色Id
     */
    private String roleId;

    /**
     * 所在省份编码
     */
    private String provinceCode;

    /**
     * 所在城市编码
     */
    private String cityCode;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 电话
     */
    private String phone;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录错误次数，最高5次
     */
    private Integer loginErrorCount;

    /**
     * 状态 0：正常 1：锁定
     */
    private String status;

    /**
     * 用户类型：1：房东  2：租户
     */
    private String type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 删除标记 0：未删除，1：已删除
     */
    private Integer delFlag;


}
