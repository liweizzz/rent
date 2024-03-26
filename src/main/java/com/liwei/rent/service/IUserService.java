package com.liwei.rent.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwei.rent.common.dto.UserBaseInfo;
import com.liwei.rent.common.dto.UserDTO;
import com.liwei.rent.entity.User;
import com.liwei.rent.common.vo.UserVO;
import com.liwei.rent.common.vo.PageVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 房东表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
public interface IUserService extends IService<User> {
    void saveOrUpdateUser(UserVO userVO);
    PageDTO<UserDTO> listUser(UserVO userVO, PageVO pageVO);
    UserBaseInfo login(String userName, String password);
    UserBaseInfo getUserInfo(String token);
    void logOut(HttpServletRequest httpRequest);
}