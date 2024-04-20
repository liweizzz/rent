package com.liwei.rent.service.impl;

import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.vo.RoleVO;
import com.liwei.rent.entity.Role;
import com.liwei.rent.dao.RoleMapper;
import com.liwei.rent.entity.RolePrivilege;
import com.liwei.rent.service.IRolePrivilegeService;
import com.liwei.rent.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-03-02
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Autowired
    private IRolePrivilegeService rolePrivilegeService;

    @Transactional
    @Override
    public void addOrUpdate(RoleVO roleVO) {
        Role role;
        LocalDateTime now = LocalDateTime.now();
        if(roleVO.getId() != null && roleVO.getId() != 0){
            role = this.getById(roleVO.getId());
            role.setUpdateTime(now);
        }else {
            role = new Role();
            role.setRoleName(roleVO.getRoleName());
            role.setCreateTime(now);
            role.setUpdateTime(now);
            role.setDelFlag(DelFlagEnum.UN_DEL.value());
        }
        this.saveOrUpdate(role);
        this.saveOrUpdateRolePrivilege(role.getId(),roleVO.getPrivilegeIdList());
    }

    @Override
    @Transactional
    public void deleteRole(Integer roleId) {
        this.removeById(roleId);
        rolePrivilegeService.lambdaUpdate().eq(RolePrivilege::getRoleId,roleId).remove();
    }

    private void saveOrUpdateRolePrivilege(Integer roleId, List<Integer> privilegeIdList){
        LocalDateTime now = LocalDateTime.now();
        List<RolePrivilege> list = privilegeIdList.stream().map(privilegeId -> {
            RolePrivilege rolePrivilege;
            RolePrivilege one = rolePrivilegeService.lambdaQuery().eq(RolePrivilege::getRoleId, roleId)
                    .eq(RolePrivilege::getPrivilegeId, privilegeId)
                    .eq(RolePrivilege::getDelFlag, DelFlagEnum.UN_DEL.value())
                    .one();
            if (one != null) {
                rolePrivilege = one;
            } else {
                rolePrivilege = new RolePrivilege();
                rolePrivilege.setCreateTime(now);
                rolePrivilege.setDelFlag(DelFlagEnum.UN_DEL.value());
            }
            rolePrivilege.setRoleId(roleId);
            rolePrivilege.setPrivilegeId(privilegeId);
            rolePrivilege.setUpdateTime(now);
            return rolePrivilege;
        }).collect(Collectors.toList());
        rolePrivilegeService.saveOrUpdateBatch(list);
    }
}
