package com.liwei.rent.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleVO {
    private Integer id;

    private String roleName;
    //权限Id
    private List<Integer> privilegeIdList;
}
