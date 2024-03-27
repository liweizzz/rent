package com.liwei.rent.dao;

import com.liwei.rent.common.dto.TenantSimpleDTO;
import com.liwei.rent.entity.Tenant;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

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
    //获取公寓下 住户、房间号
    List<TenantSimpleDTO> getAllTenantSimpleData(String apartmentId);
}
