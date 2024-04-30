package com.liwei.rent.common.intercepter;

import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.annotation.PermissionCheck;
import com.liwei.rent.common.constant.RentConstant;
import com.liwei.rent.common.dto.UserBaseInfo;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.EncryptUtils;
import com.liwei.rent.common.utils.RedisUtils;
import com.liwei.rent.entity.Privilege;
import com.liwei.rent.entity.RolePrivilege;
import com.liwei.rent.service.IPrivilegeService;
import com.liwei.rent.service.IRolePrivilegeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private IPrivilegeService privilegeService;
    @Autowired
    private IRolePrivilegeService rolePrivilegeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        PermissionCheck permission;
        if(handler instanceof HandlerMethod){
            permission = ((HandlerMethod) handler).getMethodAnnotation(PermissionCheck.class);
        }else {
            return true;
        }
        if(permission == null){
            return true;
        }
        return this.hasAuthorization(permission.value(), token);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    //鉴权
    private boolean hasAuthorization(String permissionCode,String token) {
        if(StringUtils.isEmpty(token)){
            throw new RentException(ErrorCodeEnum.USER_INVALID_TOKEN);
        }
        String userId = EncryptUtils.decrypt(token, RentConstant.KEY);
        UserBaseInfo userBaseInfo = (UserBaseInfo)redisUtils.get(userId);
        Privilege privilege = privilegeService.lambdaQuery().eq(Privilege::getCode, permissionCode).one();
        if(privilege == null){
            throw new RentException(ErrorCodeEnum.USER_PERMISSION_DENIED);
        }
        String roleId = userBaseInfo.getRoleId();
        boolean flag = rolePrivilegeService.lambdaQuery().eq(RolePrivilege::getRoleId, roleId)
                .eq(RolePrivilege::getPrivilegeId, privilege.getId())
                .eq(RolePrivilege::getDelFlag, DelFlagEnum.UN_DEL.value()).exists();
        if(!flag){
            throw new RentException(ErrorCodeEnum.USER_PERMISSION_DENIED);
        }
        return flag;
    }
}
