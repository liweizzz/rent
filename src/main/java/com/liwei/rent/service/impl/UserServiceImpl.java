package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.Enum.UserStatusEnum;
import com.liwei.rent.common.constant.RentConstant;
import com.liwei.rent.common.dto.ApartmentDTO;
import com.liwei.rent.common.dto.PrivilegeDTO;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.*;
import com.liwei.rent.dao.PrivilegeMapper;
import com.liwei.rent.dao.UserMapper;
import com.liwei.rent.common.dto.UserBaseInfo;
import com.liwei.rent.common.dto.UserDTO;
import com.liwei.rent.entity.*;
import com.liwei.rent.service.*;
import com.liwei.rent.common.vo.UserVO;
import com.liwei.rent.common.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 房东表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-01-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IdUtils idUtils;
    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICityService cityService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private PrivilegeMapper privilegeMapper;
    @Autowired
    private IApartmentService apartmentService;
    @Autowired
    private IRoleService roleService;
    @Value("${key.file}")
    private String keyFile;
    @Autowired
    private ILoginLogService loginLogService;

    @Override
    public void saveOrUpdateUser(UserVO userVO) {
        if(userVO == null){
            logger.info("用户信息为空");
            return;
        }
        User user;
        //新增
        if(userVO.getId() == null || userVO.getId() == 0){
            user = new User();
            BeanUtils.copyProperties(userVO,user);
            user.setUserId(idUtils.generateUserId());
            user.setStatus(UserStatusEnum.NORMAL.value());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            user.setDelFlag(DelFlagEnum.UN_DEL.value());
        }else {//修改
            user = this.getById(userVO.getId());
            BeanUtils.copyProperties(userVO,user);
            user.setUpdateTime(new Date());
        }
        this.saveOrUpdate(user);
    }

    @Override
    public PageDTO<UserDTO> listUser(UserVO userVO, PageVO pageVO) {
        PageDTO<UserDTO> res = new PageDTO<>();
        PageDTO<User> page = new PageDTO<>();
        page.setCurrent(pageVO.getPageNum());
        page.setSize(pageVO.getPageSize());
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        //房东ID
        if(StringUtils.isNotEmpty(userVO.getUserId())){
            cond = cond.eq(User::getUserId, userVO.getUserId());
        }
        //房东姓名
        if(StringUtils.isNotEmpty(userVO.getUserName())){
            cond = cond.eq(User::getUserName, userVO.getUserName());
        }
        //电话
        if(StringUtils.isNotEmpty(userVO.getPhone())){
            cond = cond.eq(User::getPhone, userVO.getPhone());
        }
        //省
        if(StringUtils.isNotEmpty(userVO.getProvinceCode())){
            cond = cond.eq(User::getProvinceCode, userVO.getProvinceCode());
        }
        //城市
        if(StringUtils.isNotEmpty(userVO.getCityCode())){
            cond = cond.eq(User::getCityCode, userVO.getCityCode());
        }
        //状态
        if(StringUtils.isNotEmpty(userVO.getStatus())){
            cond = cond.eq(User::getDelFlag,userVO.getStatus());
        }
        cond.eq(User::getDelFlag,DelFlagEnum.UN_DEL);
        PageDTO<User> userPageDTO = this.getBaseMapper().selectPage(page, cond);
        List<User> userList = userPageDTO.getRecords();
        List<UserDTO> userDTOList = userList.stream()
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    BeanUtils.copyProperties(user, userDTO);
                    // 转换省份
                    Province province = provinceService.lambdaQuery().eq(Province::getProvinceCode, user.getProvinceCode()).one();
                    userDTO.setProvince(province != null ? province.getName() : null);
                    // 转换城市
                    City city = cityService.lambdaQuery().eq(City::getCityCode, user.getCityCode()).one();
                    userDTO.setCity(city != null ? city.getName() : null);
                    if(StringUtils.isNotEmpty(user.getRoleId())){
                        Role role = roleService.getById(user.getRoleId());
                        userDTO.setRoleName(role.getRoleName());
                    }
                    //对身份证、手机号进行掩码
                    userDTO.setIdCard(IdUtils.maskIdCard(user.getIdCard()));
                    userDTO.setPhone(IdUtils.maskPhoneNum(user.getPhone()));
                    userDTO.setAddress(IdUtils.maskAddress(user.getAddress()));
                    return userDTO;
                }).collect(Collectors.toList());
        BeanUtils.copyProperties(userPageDTO,res,"records");
        res.setRecords(userDTOList);
        return res;
    }

    @Override
    public UserBaseInfo login(String userName, String password) {
        if(StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
            throw new RentException(ErrorCodeEnum.USERNAME_OR_PASSWORD_IS_EMPTY);
        }
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getUserName,userName).eq(User::getPassword,password)
                .eq(User::getDelFlag,DelFlagEnum.UN_DEL.value());
        User user = this.getOne(cond);
        if(user == null){
            throw new RentException(ErrorCodeEnum.USERNAME_OR_PASSWORD_NOT_CORRECT);
        }
        if(UserStatusEnum.LOCK.value().equals(user.getStatus())){
            throw new RentException(ErrorCodeEnum.USER_LOCK);
        }
        String userId = user.getUserId();
        String token = generateToken(userId);
        UserBaseInfo userBaseInfo = this.wapperUserBaseInfo(user, token);
        //保存到redis
        redisUtils.set(userId, userBaseInfo,30);
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        //记录登录日志表
        ThreadPoolTaskExecutor threadPoolTaskExecutor = SpringUtils.getBean("asyncTaskExecutor", ThreadPoolTaskExecutor.class);
        threadPoolTaskExecutor.execute(()->{
            LoginLog log = new LoginLog();
            log.setUserId(user.getUserId());
            log.setIpAddress(IPUtils.getIpAddr(request));
            log.setCreateTime(LocalDateTime.now());
            log.setUpdateTime(LocalDateTime.now());
            loginLogService.save(log);
        });
        //返回token、用户基本信息给前端
        return userBaseInfo;
    }

    //生成token
    private String generateToken(String userId){
        String key;
        try {
            key = new String(Files.readAllBytes(Paths.get(keyFile)), StandardCharsets.UTF_8);
            RentConstant.KEY = key;
        } catch (IOException e) {
            throw new RentException(ErrorCodeEnum.KEY_NOT_EXIST);
        }
        return EncryptUtils.encrypt(userId, key);
    }

    @Override
    public UserBaseInfo getUserInfo(String token) {
        if(StringUtils.isEmpty(token)){
            throw new RentException(ErrorCodeEnum.USER_INVALID_TOKEN);
        }
        String userId = EncryptUtils.decrypt(token, RentConstant.KEY);
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getUserId,userId);
        User user = this.getOne(cond);
        return this.wapperUserBaseInfo(user,token);
    }

    @Override
    public void logOut(HttpServletRequest httpRequest){
        String header = httpRequest.getHeader("X-Token");
        if(StringUtils.isEmpty(header)){
            String userId = EncryptUtils.decrypt(header, RentConstant.KEY);
            redisUtils.delete(userId);
        }
    }

    @Override
    public UserDTO getUser(Integer id) {
        UserDTO userDTO = new UserDTO();
        User user = this.getById(id);
        if(user == null){
            throw new RentException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        BeanUtils.copyProperties(user,userDTO);
        //转换省份
        Province province = provinceService.getOne(new LambdaQueryWrapper<Province>().eq(Province::getProvinceCode, user.getProvinceCode()));
        userDTO.setProvince(province != null ? province.getProvinceCode() : null);
        // 转换城市
        City city = cityService.getOne(new LambdaQueryWrapper<City>().eq(City::getCityCode, user.getCityCode()));
        userDTO.setCity(city != null ? city.getCityCode() : null);
        if(StringUtils.isNotEmpty(user.getRoleId())){
            Role role = roleService.getById(user.getRoleId());
            userDTO.setRoleName(role.getRoleName());
        }
        return userDTO;
    }

    private UserBaseInfo wapperUserBaseInfo(User user,String token){
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        //获取当前用户登录的角色
        String roleId = user.getRoleId();
        if(StringUtils.isEmpty(roleId)){
            throw new RentException(ErrorCodeEnum.USER_ROLE_IS_NULL);
        }
        userBaseInfo.setRoleId(roleId);
        //获取角色的所有权限
        List<Privilege> privilegeList = privilegeMapper.getFromRoleIds(roleId);
        List<PrivilegeDTO> privileges = privilegeList.stream().map(x -> {
            PrivilegeDTO privilegeDTO = new PrivilegeDTO();
            BeanUtils.copyProperties(x, privilegeDTO);
            return privilegeDTO;
        }).collect(Collectors.toList());
        //构建权限菜单树
        userBaseInfo.setPrivileges(MenuUtils.buildMenuTree(privileges));
        //获取当前登录用户所有公寓
        List<ApartmentDTO> apartmentDTOS = apartmentService.listApartmentByUserId(user.getUserId());
        userBaseInfo.setApartments(apartmentDTOS);
        userBaseInfo.setUserId(user.getUserId());
        userBaseInfo.setUsername(user.getUserName());
        userBaseInfo.setToken(token);
        return userBaseInfo;
    }

}
