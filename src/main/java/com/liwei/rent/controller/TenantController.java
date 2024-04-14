package com.liwei.rent.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.TenantDTO;
import com.liwei.rent.common.dto.TenantSimpleDTO;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.entity.Tenant;
import com.liwei.rent.service.ITenantService;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.TenantVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 租户表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-01-21
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {
    private static final Logger logger = LoggerFactory.getLogger(TenantController.class);

    @Autowired
    private ITenantService tenantService;

    @PostMapping(value = "/saveOrUpdate")
    public Result<Void> addTenant(@RequestBody TenantVO tenantVO){
        logger.info("新增/修改租户入参：{}", JSON.toJSONString(tenantVO));
        tenantService.saveOrUpdateTenant(tenantVO);
        return Result.ok();
    }

    @GetMapping(value = "/list")
    public Result<PageDTO<TenantDTO>> listTenant(@ModelAttribute("tenantVO") TenantVO tenantVO, @ModelAttribute("pageVO") PageVO pageVO){
        logger.info("查询租户列表入参：{}", JSON.toJSONString(tenantVO));
        PageDTO<TenantDTO> res = new PageDTO<>();
        PageDTO<Tenant> tenantPage = tenantService.listTenant(tenantVO, pageVO);
        //对身份证进行掩码
        tenantPage.getRecords().forEach(tenant -> {
            if(StringUtils.isNotEmpty(tenant.getIdCard())){
                tenant.setIdCard(IdUtils.maskIdCard(tenant.getIdCard()));
            }
        });
        BeanUtils.copyProperties(tenantPage,res);
        return Result.build(res);
    }

    @DeleteMapping(value = "/del")
    public Result<Void> delete(Integer id){
        tenantService.delTenant(id);
        return Result.ok();
    }

    /**
     * 查询公寓下的所有租户
     * @param apartmentId
     * @return
     */
    @GetMapping(value = "/listAllTenantFromApartment")
    public Result<List<TenantSimpleDTO>> listAllTenantFromApartment(String apartmentId){
        return Result.build(tenantService.getAllTenantSimpleData(apartmentId));
    }

    @GetMapping(value = "/getTenantInfo")
    public Result<TenantDTO> getTenantInfo(Integer id){
        TenantDTO res = new TenantDTO();
        Tenant tenant = tenantService.getById(id);
        if(tenant != null){
            BeanUtils.copyProperties(tenant,res);
        }
        return Result.build(res);
    }

}
