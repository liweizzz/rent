package com.liwei.rent.common.utils;

import com.liwei.rent.common.dto.UserBaseInfo;

public class UserContext {
    private static final ThreadLocal<UserBaseInfo> userThreadLocal = new ThreadLocal<>();

    public static void setUserBaseInfo(UserBaseInfo userBaseInfo){
        userThreadLocal.set(userBaseInfo);
    }

    public static UserBaseInfo getUserBaseInfo(){
        return userThreadLocal.get();
    }

    public static void clear(){
        userThreadLocal.remove();
    }
}
