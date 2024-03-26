package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.TenantStatusEnum;
import com.liwei.rent.entity.Tenant;
import com.liwei.rent.dao.TenantMapper;
import com.liwei.rent.service.ITenantService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.utils.DateUtils;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.TenantVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {
    private static final Logger logger = LoggerFactory.getLogger(TenantServiceImpl.class);

    @Autowired
    private IdUtils idUtils;

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
    public PageDTO<Tenant> listTenant(TenantVO userVO, PageVO pageVO) {
        PageDTO<Tenant> page = new PageDTO<>();
        page.setPages(pageVO.getPageNum());
        page.setSize(pageVO.getPageSize());
        LambdaQueryWrapper<Tenant> cond = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(userVO.getUserId())){
            cond.eq(Tenant::getUserId,userVO.getUserId());
        }
        cond.eq(Tenant::getDelFlag,DelFlagEnum.UN_DEL.value());
        return this.baseMapper.selectPage(page,cond);
    }

}
