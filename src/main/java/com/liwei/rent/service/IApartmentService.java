package com.liwei.rent.service;

import com.liwei.rent.common.dto.ApartmentDTO;
import com.liwei.rent.entity.Apartment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwei.rent.common.vo.ApartmentVO;

import java.util.List;

/**
 * <p>
 * 公寓表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-02-22
 */
public interface IApartmentService extends IService<Apartment> {
    void saveOrUpdateApartment(ApartmentVO apartmentVO);
    List<ApartmentDTO> listApartmentByUserId(String landlordId);
}
