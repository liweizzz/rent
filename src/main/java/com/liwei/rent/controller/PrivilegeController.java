package com.liwei.rent.controller;


import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.PrivilegeDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.utils.MenuUtils;
import com.liwei.rent.entity.Privilege;
import com.liwei.rent.service.IPrivilegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-03-20
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    @Autowired
    private IPrivilegeService privilegeService;

    @GetMapping(value = "/getAll")
    public Result<List<PrivilegeDTO>> getAllPrivilege(){
        List<PrivilegeDTO> privilegeDTOList = privilegeService.lambdaQuery().eq(Privilege::getDelFlag, DelFlagEnum.UN_DEL).list().stream().map(privilege -> {
            PrivilegeDTO privilegeDTO = new PrivilegeDTO();
            BeanUtils.copyProperties(privilege, privilegeDTO);
            return privilegeDTO;
        }).collect(Collectors.toList());
        //构建菜单树
        return Result.build(MenuUtils.buildMenuTree(privilegeDTOList));
    }

}
