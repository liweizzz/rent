package com.liwei.rent.controller;


import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.ITenantRentDetailService;
import com.liwei.rent.common.vo.TenantRentDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户租房明细表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/tenantRentDetail")
public class TenantRentDetailController {
    private static final Logger logger = LoggerFactory.getLogger(TenantRentDetailController.class);
    @Autowired
    private ITenantRentDetailService tenantRentDetailService;

    @PostMapping(value = "/saveOrUpdate")
    public Result<Void> saveTenantRentDetail(@RequestBody TenantRentDetailVO tenantRentDetailVO){
        tenantRentDetailService.saveTenantRentDetail(tenantRentDetailVO);
        return Result.ok();
    }
}
