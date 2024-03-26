package com.liwei.rent.service.impl;

import com.liwei.rent.common.dto.PrivilegeDTO;
import com.liwei.rent.entity.Privilege;
import com.liwei.rent.dao.PrivilegeMapper;
import com.liwei.rent.service.IPrivilegeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-03-20
 */
@Service
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeMapper, Privilege> implements IPrivilegeService {

}
