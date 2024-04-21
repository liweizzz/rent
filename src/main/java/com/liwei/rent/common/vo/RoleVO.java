package com.liwei.rent.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleVO {
    private Integer id;

    @NotBlank(message = "角色名不能为空")
    private String roleName;
    //权限Id
    private List<Integer> privilegeIdList;
}
