package com.liwei.rent.dao;

import com.liwei.rent.entity.Privilege;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author liwei
 * @since 2024-03-20
 */
public interface PrivilegeMapper extends BaseMapper<Privilege> {
    List<Privilege> getFromRoleIds(List<Integer> roleIds);
}
