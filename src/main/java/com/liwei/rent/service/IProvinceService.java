package com.liwei.rent.service;

import com.liwei.rent.common.dto.ProvinceDTO;
import com.liwei.rent.entity.Province;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 省份信息表 服务类
 * </p>
 *
 * @author author
 * @since 2024-02-08
 */
public interface IProvinceService extends IService<Province> {
    List<ProvinceDTO> getAllAres();
}
