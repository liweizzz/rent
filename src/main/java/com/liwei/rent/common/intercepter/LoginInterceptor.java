package com.liwei.rent.common.intercepter;

import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.constant.RentConstant;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.EncryptUtils;
import com.liwei.rent.common.utils.RedisUtils;
import com.liwei.rent.common.dto.UserBaseInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前进行拦截处理
        // 这里可以进行登录验证，比如检查用户的登录状态或权限
        // 如果用户未登录或权限不足，可以重定向到登录页面或返回错误信息
        if (!isUserLoggedIn(request)) {
            throw new RentException(ErrorCodeEnum.USER_NO_TOKEN);
        }
        return true; // 放行该请求，继续处理
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    // 检查用户是否已经登录的逻辑
    // 返回true表示已登录，返回false表示未登录
    private boolean isUserLoggedIn(HttpServletRequest request) {
        String header = request.getHeader("X-Token");
        if(StringUtils.isEmpty(header)){
            return false;
        }
        String userId = EncryptUtils.decrypt(header, RentConstant.KEY);
        UserBaseInfo userBaseInfo = (UserBaseInfo)redisUtils.get(userId);
        if(userBaseInfo != null){
            redisUtils.set(userId, userBaseInfo,30);
        }
        return userBaseInfo != null ? true :false;
    }
}
