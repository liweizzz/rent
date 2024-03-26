package com.liwei.rent.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.liwei.rent.dao.ProvinceMapper;
import com.liwei.rent.common.dto.CityDTO;
import com.liwei.rent.common.dto.ProvinceDTO;
import com.liwei.rent.entity.City;
import com.liwei.rent.entity.Province;
import com.liwei.rent.service.ICityService;
import com.liwei.rent.service.IProvinceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.utils.RedisUtils;
import com.liwei.rent.common.constant.RentConstant;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 省份信息表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-02-08
 */
@Service
public class ProvinceServiceImpl extends ServiceImpl<ProvinceMapper, Province> implements IProvinceService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private ICityService cityService;

    @Override
    public List<ProvinceDTO> getAllAres() {
        List<ProvinceDTO> res;
        Object proAndCityCache = redisUtils.get(RentConstant.PROVINCE_CITY_KEY);
        if (ObjectUtils.isNotEmpty(proAndCityCache)) {
            res = JSONArray.parseArray(proAndCityCache.toString(),ProvinceDTO.class);
        }else {
            List<Province> provinceList = this.list();
            List<City> cityList = cityService.list();
            res = provinceList.stream().map(province -> {
                ProvinceDTO provinceDTO = new ProvinceDTO();
                BeanUtils.copyProperties(province, provinceDTO);
                provinceDTO.setCode(province.getProvinceCode());

                List<CityDTO> cityDTOList = cityList.stream().filter(city -> city.getProvinceId() == province.getId()).map(city -> {
                    CityDTO cityDTO = new CityDTO();
                    BeanUtils.copyProperties(city, cityDTO);
                    cityDTO.setCode(city.getCityCode());
                    return cityDTO;
                }).collect(Collectors.toList());

                provinceDTO.setCityList(cityDTOList);
                return provinceDTO;
            }).collect(Collectors.toList());
            redisUtils.set(RentConstant.PROVINCE_CITY_KEY, JSONObject.toJSONString(res));
        }
        return res;
    }
}
