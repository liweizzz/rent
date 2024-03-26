package com.liwei.rent.dao;

import com.liwei.rent.entity.Apartment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 公寓表 Mapper 接口
 * </p>
 *
 * @author liwei
 * @since 2024-02-22
 */
public interface ApartmentMapper extends BaseMapper<Apartment> {
    String getLatestApartmentId();
}
