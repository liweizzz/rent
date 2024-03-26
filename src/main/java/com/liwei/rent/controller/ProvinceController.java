package com.liwei.rent.controller;

import com.liwei.rent.common.dto.ProvinceDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.IProvinceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 省份信息表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-08
 */
@RestController
@RequestMapping("/province")
public class ProvinceController {
    private static final Logger loger = LoggerFactory.getLogger(ProvinceController.class);

    @Autowired
    private IProvinceService provinceService;

    @GetMapping("/getAllAreas")
    public Result<List<ProvinceDTO>> getAllAreas(){
        List<ProvinceDTO> allAres = provinceService.getAllAres();
        return Result.build(allAres);
    }

}
