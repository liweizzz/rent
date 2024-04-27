package com.liwei.rent.controller;

import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.dto.UserBaseInfo;
import com.liwei.rent.common.vo.LoginVO;
import com.liwei.rent.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth/user")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private IUserService userService;

    @PostMapping(value = "/login")
    public Result<UserBaseInfo> login(@Valid @RequestBody LoginVO loginVO){
        UserBaseInfo res = userService.login(loginVO.getUsername(), loginVO.getPassword());
        return Result.build(res);
    }

    @GetMapping(value = "/info")
    public Result<UserBaseInfo> getUserInfo(HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("X-Token");
        UserBaseInfo userInfo = userService.getUserInfo(token);
        return Result.build(userInfo);
    }

    @PostMapping(value = "/logOut")
    public Result<Void> loginOut(HttpServletRequest httpRequest){
        userService.logOut(httpRequest);
        return Result.ok();
    }
}
