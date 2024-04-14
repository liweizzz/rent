package com.liwei.rent.service.impl;

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
    public void saveOrUpdateTenantRentDetail(TenantRentDetailVO tenantRentDetailVO) {
        String tenantId = tenantRentDetailVO.getTenantId();
        String roomNum = tenantRentDetailVO.getRoomNum();
        if(this.checkRoomHashTenant(roomNum,tenantId)){
            throw new RentException(ErrorCodeEnum.TENANT_ROOM_BIND);
        }
        TenantRentDetail one = this.lambdaQuery().eq(TenantRentDetail::getTenantId, tenantId)
                .eq(TenantRentDetail::getDelFlag, DelFlagEnum.UN_DEL).one();
        TenantRentDetail tenantRentDetail;
        if(one == null){
            //新增
            tenantRentDetail = new TenantRentDetail();
            tenantRentDetail.setCreateTime(LocalDateTime.now());
            tenantRentDetail.setUpdateTime(LocalDateTime.now());
            tenantRentDetail.setDelFlag(DelFlagEnum.UN_DEL.value());
        }else {
            //修改
            tenantRentDetail = one;
            tenantRentDetail.setUpdateTime(LocalDateTime.now());
        }
        BeanUtils.copyProperties(tenantRentDetailVO,tenantRentDetail);
        this.saveOrUpdate(tenantRentDetail);
    }

    /**
     * 检查房间是否已绑定其他租户
     * @param roomNum
     * @param tenantId
     * @return true:已绑定，false：未绑定
     */
    public boolean checkRoomHashTenant(String roomNum,String tenantId){
        TenantRentDetail one = this.lambdaQuery().eq(TenantRentDetail::getRoomNum, roomNum)
                .eq(TenantRentDetail::getDelFlag, DelFlagEnum.UN_DEL).one();
        if(one == null || tenantId.equals(one.getTenantId())){
            return false;
        }
        return true;
    }

}
