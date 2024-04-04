package com.liwei.rent.service;

import com.liwei.rent.common.vo.TenantRentDetailVO;
import com.liwei.rent.entity.TenantRentDetail;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户租房明细表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
public interface ITenantRentDetailService extends IService<TenantRentDetail> {
    void saveTenantRentDetail(TenantRentDetailVO tenantRentDetailVO);
}
