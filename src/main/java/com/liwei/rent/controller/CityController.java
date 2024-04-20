package com.liwei.rent.controller;


import com.liwei.rent.common.dto.CityDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.entity.City;
import com.liwei.rent.entity.Province;
import com.liwei.rent.service.ICityService;
import com.liwei.rent.service.IProvinceService;
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
    @Autowired
    private IProvinceService provinceService;

    @GetMapping("/getAllCitysByProvinceCode")
    public Result<List<CityDTO>> getAllCitysByProvince(String provinceCode){
        Province province = provinceService.lambdaQuery().eq(Province::getProvinceCode, provinceCode).one();
        List<City> list = cityService.lambdaQuery().eq(City::getProvinceId, province.getId()).list();

        List<CityDTO> res = list.stream().map(x -> {
            CityDTO cityDTO = new CityDTO();
            BeanUtils.copyProperties(x, cityDTO);
            cityDTO.setCode(x.getCityCode());
            return cityDTO;
        }).collect(Collectors.toList());
        return Result.build(res);
    }
}
