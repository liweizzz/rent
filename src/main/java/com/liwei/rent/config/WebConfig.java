package com.liwei.rent.config;

import com.liwei.rent.common.intercepter.LogInterceptor;
import com.liwei.rent.common.intercepter.LoginInterceptor;
import com.liwei.rent.common.intercepter.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/auth/user/login") // 排除登录页面的请求
                .order(Ordered.LOWEST_PRECEDENCE);
        registry.addInterceptor(logInterceptor);
        registry.addInterceptor(permissionInterceptor)
                .addPathPatterns("/**") // 拦截所有请求
                .excludePathPatterns("/auth/user/login"); // 排除登录页面的请求;
    }

}
