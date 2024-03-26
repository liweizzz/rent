package com.liwei.rent.dao;

import com.liwei.rent.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
public interface TenantMapper extends BaseMapper<Tenant> {
    //获取最新的userID
    String getLatestTenantId();
}
