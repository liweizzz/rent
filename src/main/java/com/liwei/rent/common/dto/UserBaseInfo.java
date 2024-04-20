package com.liwei.rent.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserBaseInfo {
    private String token;
    private String userId;
    private String username;
    private String roleId;
    private List<PrivilegeDTO> privileges;
    private List<ApartmentDTO> apartments;
}
