package com.liwei.rent.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liwei.rent.common.dto.CityDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.entity.City;
import com.liwei.rent.service.ICityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 城市表 前端控制器
 * </p>
 *
 * @author author
 * @since 2024-02-08
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private ICityService cityService;

    @GetMapping("/getAllCitysByProvinceId")
    public Result<List<CityDTO>> getAllCitysByProvinceId(String provinceId){
        LambdaQueryWrapper<City> cond = new LambdaQueryWrapper<>();
        cond.eq(City::getProvinceId,provinceId);
        List<City> list = cityService.list(cond);
        List<CityDTO> res = list.stream().map(x -> {
            CityDTO cityDTO = new CityDTO();
            BeanUtils.copyProperties(x, cityDTO);
            cityDTO.setCode(x.getCityCode());
            return cityDTO;
        }).collect(Collectors.toList());
        return Result.build(res);
    }
}
