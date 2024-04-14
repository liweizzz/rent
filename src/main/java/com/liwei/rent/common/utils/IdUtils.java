package com.liwei.rent.common.utils;

import com.liwei.rent.common.constant.RentConstant;
import com.liwei.rent.dao.ApartmentMapper;
import com.liwei.rent.dao.TenantMapper;
import com.liwei.rent.dao.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class IdUtils {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private ApartmentMapper apartmentMapper;
    @Autowired
    private DistributedRedisLock redisLock;

    /**
     * 自动生成房东(用户)Id：U***
     * @return
     */
    public String generateUserId(){
        String format;
        if(redisUtils.existKey(RentConstant.USER_ID_KEY)){
            Long increment = redisUtils.increment(RentConstant.USER_ID_KEY, 1);
            format = String.format("%02d", increment);
        }else {
            try {
                redisLock.lock(RentConstant.USER_ID_LOCK_KEY);
                String userId = userMapper.getLatestUserId();
                //取后2位+1
                Integer num = StringUtils.isEmpty(userId)? 1 : Integer.valueOf(userId.substring(userId.length()-2,userId.length())) + 1;
                redisUtils.set(RentConstant.USER_ID_KEY,num);
                format = String.format("%02d", num);
            } finally {
                redisLock.unlock(RentConstant.USER_ID_LOCK_KEY);
            }
        }
        return "U" + format;
    }

    /**
     * 自动生成tenantId：U***-T***
     * @param userId
     * @return
     */
    public String generateTenantId(String userId){
        String format;
        String key = RentConstant.TENANT_ID_KEY + userId;
        if(redisUtils.existKey(key)){
            Long increment = redisUtils.increment(key, 1);
            format = String.format("%02d", increment);
        }else {
            String tenantId = tenantMapper.getLatestTenantId();
            //取后2位+1
            Integer num = StringUtils.isEmpty(tenantId)? 1 : Integer.valueOf(tenantId.substring(tenantId.length()-2,tenantId.length())) + 1;
            redisUtils.set(key,num);
            format = String.format("%02d", num);
        }
        return userId + "-T" + format;
    }

    /**
     * 自动生成公寓ID：U***-A**
     * @param userId
     * @return
     */
    public String generateApartmentId(String userId){
        String format = "";
        String key = RentConstant.APARTMENT_ID_KEY + userId;
        if(redisUtils.existKey(key)){
            Long increment = redisUtils.increment(key, 1);
            format = String.format("%02d", increment);
        }else {
            String apartmentId = apartmentMapper.getLatestApartmentId();
            //取后2位+1
            Integer num = StringUtils.isEmpty(apartmentId)? 1 : Integer.valueOf(apartmentId.substring(apartmentId.length()-2,apartmentId.length())) + 1;
            redisUtils.set(key,num);
            format = String.format("%02d", num);
        }
        return userId+"-A" + format;
    }

    public static String maskIdCard(String idCard){
        Pattern pattern = Pattern.compile("(\\d{10})(\\d{4})(.*)");
        Matcher matcher = pattern.matcher(idCard);
        if (matcher.matches()) {
            // 使用星号替换匹配到的中间4位，并保留其他部分
            return matcher.replaceFirst(matcher.group(1) + "****" + matcher.group(3));
        }
        return null;
    }

}
