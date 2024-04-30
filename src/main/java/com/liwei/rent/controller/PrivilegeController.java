package com.liwei.rent.controller;


import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.PrivilegeDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.entity.Privilege;
import com.liwei.rent.service.IPrivilegeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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
        return Result.build(buildMenuTree(privilegeDTOList));
    }

    private List<PrivilegeDTO> buildMenuTree(List<PrivilegeDTO> menus) {
        // 将每个菜单按照其父菜单ID分组存储到 Map 中
        Map<Integer, List<PrivilegeDTO>> parentChildrenMap = menus.stream()
                .collect(Collectors.groupingBy(PrivilegeDTO::getParent));
        // 递归构建菜单树
        // 假设根菜单的父菜单ID为0
        return buildMenuTree(parentChildrenMap, 0);
    }

    private List<PrivilegeDTO> buildMenuTree(Map<Integer, List<PrivilegeDTO>> parentChildrenMap, Integer parentId) {
        List<PrivilegeDTO> children = parentChildrenMap.get(parentId);
        if (children == null) {
            return null;
        }
        return children.stream()
                .peek(child -> child.setChildren(buildMenuTree(parentChildrenMap, child.getId())))
                .collect(Collectors.toList());
    }

}
