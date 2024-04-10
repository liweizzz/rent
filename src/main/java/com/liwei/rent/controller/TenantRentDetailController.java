package com.liwei.rent.controller;


import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.TenantRentDetailDTO;
import com.liwei.rent.entity.TenantRentDetail;
import com.liwei.rent.service.ITenantRentDetailService;
import com.liwei.rent.common.vo.TenantRentDetailVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/getTRDByTId")
    public Result<TenantRentDetailDTO> getTenantRentDetailByTId(@RequestParam String tenantId){
        TenantRentDetailDTO rentDetailDTO = new TenantRentDetailDTO();
        TenantRentDetail entity = tenantRentDetailService.lambdaQuery().eq(TenantRentDetail::getTenantId,tenantId).one();
        if(entity != null){
            BeanUtils.copyProperties(entity,rentDetailDTO);
        }
        return Result.build(rentDetailDTO);
    }
}
