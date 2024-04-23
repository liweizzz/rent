package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.Enum.TenantStatusEnum;
import com.liwei.rent.common.dto.TenantSimpleDTO;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.dao.ApartmentMapper;
import com.liwei.rent.entity.Tenant;
import com.liwei.rent.dao.TenantMapper;
import com.liwei.rent.entity.TenantRentDetail;
import com.liwei.rent.service.ITenantRentDetailService;
import com.liwei.rent.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.utils.DateUtils;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.TenantVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@Service
@Slf4j
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {
    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Autowired
    private IdUtils idUtils;
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private ITenantRentDetailService tenantRentDetailService;

    @Override
    public boolean saveOrUpdateTenant(TenantVO tenantVO) {
        if(tenantVO == null){
            logger.info("租户信息为空");
            return false;
        }
        Tenant tenant;
        //新增
        if(tenantVO.getId() == null || tenantVO.getId() == 0){
            tenant = new Tenant();
            BeanUtils.copyProperties(tenantVO,tenant);
            tenant.setTenantId(idUtils.generateTenantId(tenantVO.getUserId()));
            //设置公寓名称
            tenant.setApartmentName(apartmentMapper.getApartmentName(tenantVO.getApartmentId()));
            tenant.setDelFlag(DelFlagEnum.UN_DEL.value());
            tenant.setStatus(TenantStatusEnum.ON_RENT.value());
            tenant.setCreateTime(DateUtils.getDate());
            tenant.setUpdateTime(DateUtils.getDate());
        }else {//修改
            tenant = this.getById(tenantVO.getId());
            BeanUtils.copyProperties(tenantVO,tenant);
            tenant.setUpdateTime(DateUtils.getDate());
        }
        return this.saveOrUpdate(tenant);
    }

    @Override
    public List<TenantSimpleDTO> getAllTenantSimpleData(String apartmentId) {
        List<TenantSimpleDTO> list = this.baseMapper.getAllTenantSimpleData(apartmentId);
        if(CollectionUtils.isEmpty(list)){
            log.info("getAllTenantSimpleData，公寓ID为空");
            return null;
        }
        return list;
    }

    @Override
    @Transactional
    public void delTenant(Integer id) {
        logger.info("删除租户，id：{}", id);
        Tenant tenant = this.getById(id);
        if(tenant == null){
            throw new RentException(ErrorCodeEnum.TENANT_IS_NOT_EXIST);
        }
        tenant.setStatus(TenantStatusEnum.OFF_RENT.value());
        tenant.setDelFlag(DelFlagEnum.DEL.value());
        this.updateById(tenant);
        //删除用户租房明细表
        tenantRentDetailService.lambdaUpdate()
                .eq(TenantRentDetail::getTenantId,tenant.getTenantId())
                .set(TenantRentDetail::getRentStatus, TenantStatusEnum.OFF_RENT.value())
                .set(TenantRentDetail::getDelFlag, DelFlagEnum.DEL.value()).update();
    }

    @Override
    public PageDTO<Tenant> listTenant(TenantVO userVO, PageVO pageVO) {
        PageDTO<Tenant> page = new PageDTO<>();
        page.setCurrent(pageVO.getPageNum());
        page.setSize(pageVO.getPageSize());
        LambdaQueryWrapper<Tenant> cond = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(userVO.getTenantId())){
            cond = cond.eq(Tenant::getTenantId,userVO.getTenantId());
        }
        if(StringUtils.isNotEmpty(userVO.getTenantName())){
            cond = cond.eq(Tenant::getTenantName,userVO.getTenantName());
        }
        if(StringUtils.isNotEmpty(userVO.getPhone())){
            cond = cond.eq(Tenant::getPhone,userVO.getPhone());
        }
        if(StringUtils.isNotEmpty(userVO.getStatus())){
            cond = cond.eq(Tenant::getStatus,userVO.getStatus());
        }
        cond.eq(Tenant::getApartmentId,userVO.getApartmentId()).eq(Tenant::getDelFlag,DelFlagEnum.UN_DEL.value());
        return this.baseMapper.selectPage(page,cond);
    }

}
