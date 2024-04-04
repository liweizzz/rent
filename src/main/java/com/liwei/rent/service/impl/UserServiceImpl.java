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
import com.liwei.rent.common.utils.EncryptUtils;
import com.liwei.rent.common.utils.RedisUtils;
import com.liwei.rent.dao.PrivilegeMapper;
import com.liwei.rent.dao.UserMapper;
import com.liwei.rent.common.dto.UserBaseInfo;
import com.liwei.rent.common.dto.UserDTO;
import com.liwei.rent.entity.City;
import com.liwei.rent.entity.Privilege;
import com.liwei.rent.entity.Province;
import com.liwei.rent.entity.User;
import com.liwei.rent.service.IApartmentService;
import com.liwei.rent.service.ICityService;
import com.liwei.rent.service.IProvinceService;
import com.liwei.rent.service.IUserService;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.common.vo.UserVO;
import com.liwei.rent.common.vo.PageVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
        if(StringUtils.isNotEmpty(userVO.getProvinceId())){
            Province province = provinceService.getById(userVO.getProvinceId());
            cond = cond.eq(User::getProvinceCode, province.getProvinceCode());
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
                    Province province = provinceService.getOne(new LambdaQueryWrapper<Province>().eq(Province::getProvinceCode, user.getProvinceCode()));
                    userDTO.setProvince(province != null ? province.getName() : null);
                    // 转换城市
                    City city = cityService.getOne(new LambdaQueryWrapper<City>().eq(City::getCityCode, user.getCityCode()));
                    userDTO.setCity(city != null ? city.getName() : null);
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
        redisUtils.set(userId, userBaseInfo, TimeUnit.SECONDS.toMinutes(30));
        //返回token、用户基本信息给前端
        return userBaseInfo;
    }

    //生成token
    private String generateToken(String userId){
        return EncryptUtils.encrypt(userId, RentConstant.AES_KEY);
    }

    @Override
    public UserBaseInfo getUserInfo(String token) {
        if(StringUtils.isEmpty(token)){
            throw new RentException(ErrorCodeEnum.USER_NO_TOKEN);
        }
        String userId = EncryptUtils.decrypt(token, RentConstant.AES_KEY);
        LambdaQueryWrapper<User> cond = new LambdaQueryWrapper<>();
        cond.eq(User::getUserId,userId);
        User user = this.getOne(cond);
        return this.wapperUserBaseInfo(user,token);
    }

    @Override
    public void logOut(HttpServletRequest httpRequest){
        String header = httpRequest.getHeader("X-Token");
        if(StringUtils.isEmpty(header)){
            String userId = EncryptUtils.decrypt(header, RentConstant.AES_KEY);
            redisUtils.delete(userId);
        }
    }

    private UserBaseInfo wapperUserBaseInfo(User user,String token){
        UserBaseInfo userBaseInfo = new UserBaseInfo();
        //获取当前用户登录的角色
        String roleIds = user.getRoleIds();
        if(StringUtils.isNotEmpty(roleIds)){
            String[] split = user.getRoleIds().split(",");
            List<Integer> roles = Arrays.stream(split).map(Integer::valueOf).collect(Collectors.toList());
            userBaseInfo.setRoles(roles);
            //获取角色的所有权限
            List<Privilege> privilegeList = privilegeMapper.getFromRoleIds(roles);
            List<PrivilegeDTO> privileges = privilegeList.stream().map(x -> {
                PrivilegeDTO privilegeDTO = new PrivilegeDTO();
                BeanUtils.copyProperties(x, privilegeDTO);
                return privilegeDTO;
            }).collect(Collectors.toList());
            //构建权限菜单树
            userBaseInfo.setPrivileges(this.buildMenuTree(privileges));
        }
        //获取当前登录用户所有公寓
        List<ApartmentDTO> apartmentDTOS = apartmentService.listApartmentByUserId(user.getUserId());
        userBaseInfo.setApartments(apartmentDTOS);
        userBaseInfo.setUserId(user.getUserId());
        userBaseInfo.setUsername(user.getUserName());
        userBaseInfo.setToken(token);
        return userBaseInfo;
    }

    // 构建菜单树的方法
    private List<PrivilegeDTO> buildMenuTree(List<PrivilegeDTO> menus) {
//        menus.sort(Comparator.comparing(PrivilegeDTO::getSort));
        // 创建一个 Map，用于存储菜单的父菜单ID和其子菜单列表的映射关系
//        Map<Long, List<Menu>> parentChildrenMap = new HashMap<>();
//        for (Menu menu : menus) {
//            // 将每个菜单按照其父菜单ID分组存储到 Map 中
//            parentChildrenMap.computeIfAbsent(menu.getParentId(), k -> new ArrayList<>()).add(menu);
//        }
        // 将每个菜单按照其父菜单ID分组存储到 Map 中
        Map<Integer, List<PrivilegeDTO>> parentChildrenMap = menus.stream()
                .collect(Collectors.groupingBy(PrivilegeDTO::getParent));
        // 递归构建菜单树
        return buildMenuTree(parentChildrenMap, 0); // 假设根菜单的父菜单ID为0
    }

    // 普通递归构建菜单树的辅助方法
//    private static List<Menu> buildMenuTree(Map<Long, List<Menu>> parentChildrenMap, Long parentId) {
//        List<Menu> menuTree = new ArrayList<>();
//        // 获取当前父菜单ID下的所有子菜单列表
//        List<Menu> children = parentChildrenMap.get(parentId);
//        if (children != null) {
//            // 遍历子菜单列表，递归构建菜单树
//            for (Menu child : children) {
//                child.setChildren(buildMenuTree(parentChildrenMap, child.getId())); // 递归构建子菜单的子树
//                menuTree.add(child);
//            }
//        }
//        return menuTree;
//    }

    /**
     * 使用了 Stream 的 peek 方法来处理每个子菜单
     * 并在处理过程中递归构建子菜单的子树
     * 最后，使用 collect 方法将处理过的子菜单收集到一个列表中并返回
     * @param parentChildrenMap
     * @param parentId
     * @return
     */
    private List<PrivilegeDTO> buildMenuTree(Map<Integer, List<PrivilegeDTO>> parentChildrenMap, Integer parentId) {
        List<PrivilegeDTO> children = parentChildrenMap.get(parentId);
        if (children == null) {
            return null;
        }
        return children.stream()
                .peek(child -> child.setChildren(buildMenuTree(parentChildrenMap, child.getId())))
                .collect(Collectors.toList());
    }

//    // 菜单实体类
//    @Data
//    public static class Menu {
//        private Long id;
//        private String name;
//        private Long parentId;
//        private List<Menu> children; // 子菜单列表
//
//        public Menu(Long id, String name, Long parentId) {
//            this.id = id;
//            this.name = name;
//            this.parentId = parentId;
//        }
//    }
//
//    // 示例
//    public static void main(String[] args) {
//        List<Menu> menus = new ArrayList<>();
//        Menu m1 = new Menu(1L,"系统管理",0L);
//        Menu m2 = new Menu(2L,"客户管理",1L);
//        Menu m3 = new Menu(3L,"收据管理",1L);
//        Menu m4 = new Menu(4L,"合同管理",2L);
//        Menu m5 = new Menu(5L,"电子收据",2L);
//        Menu m6 = new Menu(6L,"房东管理",0L);
//        menus.add(m1);
//        menus.add(m2);
//        menus.add(m3);
//        menus.add(m4);
//        menus.add(m5);
//        menus.add(m6);
//        // 假设 menus 是从数据库中查询出来的菜单列表
//        // 调用 buildMenuTree 方法构建菜单树
//        List<Menu> menuTree = buildMenuTree(menus);
//        // 输出菜单树
//        System.out.println(menuTree);
//    }

}
