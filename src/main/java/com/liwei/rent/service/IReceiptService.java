package com.liwei.rent.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.ReceiptVO;
import com.liwei.rent.entity.Receipt;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 收据表 服务类
 * </p>
 *
 * @author liwei
 * @since 2024-02-18
 */
public interface IReceiptService extends IService<Receipt> {
    void createReceipt(ReceiptVO receiptVO);
    PageDTO<ReceiptDTO> listReceipt(ReceiptVO receiptVO, PageVO pageVO);
    //获取收据图片
    byte[] getReceiptImg(Integer id);
    //导出报表
    void exportReceipt(String apartmentId,String month);
}
