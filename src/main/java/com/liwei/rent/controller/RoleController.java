package com.liwei.rent.controller;


import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.RoleDTO;
import com.liwei.rent.common.dto.RolePriDTO;
import com.liwei.rent.common.vo.RoleVO;
import com.liwei.rent.entity.Role;
import com.liwei.rent.entity.RolePrivilege;
import com.liwei.rent.service.IRolePrivilegeService;
import com.liwei.rent.service.IRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-03-02
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRolePrivilegeService rolePrivilegeService;

    @PostMapping(value = "/addOrUpdate")
    public Result<Void> addOrUpdateRole(@Valid @RequestBody RoleVO roleVO){
        roleService.addOrUpdate(roleVO);
        return Result.ok();
    }

    @GetMapping(value = "/getRoles")
    public Result<List<RoleDTO>> getRoles(){
        List<RoleDTO> res = new ArrayList<>();
        List<Role> list = roleService.list();
        if(!CollectionUtils.isEmpty(list)){
            res = list.stream().map(role -> {
                RoleDTO roleDTO = new RoleDTO();
                BeanUtils.copyProperties(role, roleDTO);
                return roleDTO;
            }).collect(Collectors.toList());
        }
        return Result.build(res);
    }

    @GetMapping(value = "/getRole")
    public Result<RolePriDTO> getRole(Integer id){
        RolePriDTO res = new RolePriDTO();
        Role role = roleService.getById(id);
        if(role == null){
            return Result.ok();
        }
        res.setId(role.getId());
        res.setRoleName(role.getRoleName());
        List<RolePrivilege> list = rolePrivilegeService.lambdaQuery().eq(RolePrivilege::getRoleId, id).list();
        List<Integer> collect = list.stream().map(RolePrivilege::getPrivilegeId).collect(Collectors.toList());
        res.setPrivilegeIdList(collect);
        return Result.build(res);
    }

    @GetMapping(value = "/changeStatus")
    public Result<Void> changeStatus(Integer id,Integer status){
        roleService.lambdaUpdate().set(Role::getDelFlag,status).eq(Role::getId,id).update();
        return Result.ok();
    }

    @DeleteMapping(value = "/delete")
    public Result<Void> deleteRole(Integer id){
        roleService.deleteRole(id);
        return Result.ok();
    }
}
