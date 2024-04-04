package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.vo.TenantRentDetailVO;
import com.liwei.rent.dao.TenantRentDetailMapper;
import com.liwei.rent.entity.TenantRentDetail;
import com.liwei.rent.service.ITenantRentDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户租房明细表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
@Service
@Slf4j
public class TenantRentDetailServiceImpl extends ServiceImpl<TenantRentDetailMapper, TenantRentDetail> implements ITenantRentDetailService {

    @Override
    public void saveTenantRentDetail(TenantRentDetailVO tenantRentDetailVO) {
        String tenantId = tenantRentDetailVO.getTenantId();
        if(this.checkRoomHashTenant(tenantId)){
            log.info("租户已绑定房间，tenantId:{},roomNum:{}",tenantId,tenantRentDetailVO.getRoomNum());
            throw new RentException(ErrorCodeEnum.TENANT_ROOM_BIND);
        }
        TenantRentDetail tenantRentDetail = new TenantRentDetail();
        BeanUtils.copyProperties(tenantRentDetailVO,tenantRentDetail);
        tenantRentDetail.setCreateTime(LocalDateTime.now());
        tenantRentDetail.setUpdateTime(LocalDateTime.now());
        tenantRentDetail.setDelFlag(DelFlagEnum.UN_DEL.value());
        this.saveOrUpdate(tenantRentDetail);
    }

    public boolean checkRoomHashTenant(String tenantId){
        LambdaQueryWrapper<TenantRentDetail> cond = new LambdaQueryWrapper<>();
        cond.eq(TenantRentDetail::getTenantId,tenantId)
                .eq(TenantRentDetail::getDelFlag, DelFlagEnum.UN_DEL);
        TenantRentDetail one = this.getOne(cond);
        if(one != null){
            return true;
        }
        return false;
    }

}
