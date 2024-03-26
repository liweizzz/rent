package com.liwei.rent.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserBaseInfo {
    private String token;
    private String userId;
    private String username;
    private List<Integer> roles;
    private List<PrivilegeDTO> privileges;
}
