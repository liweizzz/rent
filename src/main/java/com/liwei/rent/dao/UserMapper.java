package com.liwei.rent.dao;

import com.liwei.rent.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户(房东)表 Mapper 接口
 * </p>
 *
 * @author liwei
 * @since 2024-02-27
 */
public interface UserMapper extends BaseMapper<User> {
    String getLatestUserId();
}
