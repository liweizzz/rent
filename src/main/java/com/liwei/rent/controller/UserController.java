package com.liwei.rent.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.annotation.PermissionCheck;
import com.liwei.rent.common.dto.UserDTO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.entity.User;
import com.liwei.rent.service.IUserService;
import com.liwei.rent.common.vo.UserVO;
import com.liwei.rent.common.vo.PageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户（房东）表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/saveOrUpdate")
    public Result<Void> addOrUpdateUser(@Valid @RequestBody UserVO userVO){
        logger.info("新增/修改用户入参：{}", JSON.toJSONString(userVO));
        userService.saveOrUpdateUser(userVO);
        return Result.ok();
    }

    @GetMapping(value = "/list")
    public Result<PageDTO<UserDTO>> listUser(@ModelAttribute("userVO") UserVO userVO, @ModelAttribute("pageVO") PageVO pageVO){
        logger.info("查询用户列表入参：{}", JSON.toJSONString(userVO));
        PageDTO<UserDTO> res = userService.listUser(userVO, pageVO);
        return Result.build(res);
    }

    @GetMapping(value = "/get")
    @PermissionCheck("user:get")
    public Result<UserDTO> getUser(Integer id){
        UserDTO res = userService.getUser(id);
        return Result.build(res);
    }

    @DeleteMapping(value = "del")
    public Result<Void> delete(Integer id){
        logger.info("删除用户，id：{}", id);
        User byId = userService.getById(id);
        if(byId != null){
            byId.setDelFlag(DelFlagEnum.DEL.value());
        }
        userService.updateById(byId);
        return Result.ok();
    }

}
