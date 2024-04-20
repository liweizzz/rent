package com.liwei.rent.common.dto;

import lombok.Data;

import java.util.List;
@Data
public class RolePriDTO {
    private Integer id;

    private String roleName;
    //权限Id
    private List<Integer> privilegeIdList;
}
