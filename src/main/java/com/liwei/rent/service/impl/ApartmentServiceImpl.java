package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.ApartmentDTO;
import com.liwei.rent.entity.Apartment;
import com.liwei.rent.dao.ApartmentMapper;
import com.liwei.rent.service.IApartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.common.vo.ApartmentVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 公寓表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-02-22
 */
@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, Apartment> implements IApartmentService {
    @Autowired
    private IdUtils idUtils;

    @Override
    public void saveOrUpdateApartment(ApartmentVO apartmentVO) {
        Integer id = apartmentVO.getId();
        Apartment apartment;
        LocalDateTime now = LocalDateTime.now();
        //新增
        if(id == null || id == 0){
            apartment = new Apartment();
            BeanUtils.copyProperties(apartmentVO,apartment);
            apartment.setApartmentId(idUtils.generateApartmentId(apartmentVO.getUserId()));
            apartment.setCreateTime(now);
            apartment.setUpdateTime(now);
            apartment.setDelFlag(DelFlagEnum.UN_DEL.value());
        }else {
            //修改
            apartment = this.getById(apartmentVO.getId());
            BeanUtils.copyProperties(apartmentVO,apartment);
            apartment.setUpdateTime(now);
        }
        this.saveOrUpdate(apartment);
    }

    @Override
    public List<ApartmentDTO> listApartmentByUserId(String userId) {
        List<ApartmentDTO> res = new ArrayList<>();
        LambdaQueryWrapper<Apartment> cond = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(userId)){
            cond.eq(Apartment::getUserId,userId);
        }
        cond.eq(Apartment::getDelFlag,DelFlagEnum.UN_DEL.value());
        List<Apartment> list = this.list(cond);
        list.forEach(apartment -> {
            ApartmentDTO apartmentDTO = new ApartmentDTO();
            BeanUtils.copyProperties(apartment,apartmentDTO);
            apartmentDTO.setAddress(IdUtils.maskAddress(apartment.getAddress()));
            res.add(apartmentDTO);
        });
        return res;
    }
}
