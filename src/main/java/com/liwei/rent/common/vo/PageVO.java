package com.liwei.rent.common.vo;

import lombok.Data;

@Data
public class PageVO {
    //当前页数
    private Integer pageNum;
    //每页大小
    private Integer pageSize;
}
