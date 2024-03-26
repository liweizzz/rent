package com.liwei.rent.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.liwei.rent.common.Enum.CommonStatusEnum;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.SystemDictionaryDTO;
import com.liwei.rent.entity.SystemDictionary;
import com.liwei.rent.service.ISystemDictionaryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-03-17
 */
@RestController
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {
    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @GetMapping(value = "/listDic")
    public Result<List<SystemDictionaryDTO>> listDictionary(){
        LambdaQueryWrapper<SystemDictionary> cond = new LambdaQueryWrapper<>();
        cond.eq(SystemDictionary::getStatus, CommonStatusEnum.ON.value())
                .eq(SystemDictionary::getDelFlag, DelFlagEnum.UN_DEL.value());
        List<SystemDictionary> list = systemDictionaryService.list(cond);
        List<SystemDictionaryDTO> res = list.stream().map(x -> {
                    SystemDictionaryDTO dto = new SystemDictionaryDTO();
                    BeanUtils.copyProperties(x, dto);
                    return dto;
                }).collect(Collectors.toList());
        return Result.build(res);
    }
}
