package com.liwei.rent.service;

import com.liwei.rent.common.vo.RoleVO;
import com.liwei.rent.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-03-02
 */
public interface IRoleService extends IService<Role> {
    void addOrUpdate(RoleVO roleVO);

    void deleteRole(Integer roleId);
}
