package com.liwei.rent.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.dto.TenantSimpleDTO;
import com.liwei.rent.entity.Tenant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.TenantVO;

import java.util.List;

/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
public interface ITenantService extends IService<Tenant> {
    PageDTO<Tenant> listTenant(TenantVO tenantVO, PageVO pageVO);
    boolean saveOrUpdateTenant(TenantVO tenantVO);
    List<TenantSimpleDTO> getAllTenantSimpleData(String apartmentId);
}
