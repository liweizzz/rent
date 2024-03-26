package com.liwei.rent.common.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Before("@annotation(permissionCheck)")
    public void checkPermission(JoinPoint joinPoint, PermissionCheck permissionCheck) {
        String[] permissions = permissionCheck.value();

        // 检查用户是否具有所需的权限，这里只是一个示例
        if (!hasAuthorization(permissions)) {
            throw new SecurityException("Permission denied");
        }

    }

    // 检查用户是否具有所需的权限的方法，这里只是一个示例
    private boolean hasAuthorization(String[] permissions) {
        // 实现你的权限检查逻辑，比如从数据库或缓存中获取用户的权限信息，然后与所需的权限进行比对
        // 返回true或false表示用户是否具有所需的权限
        return true; // 假设始终返回true
    }
}
