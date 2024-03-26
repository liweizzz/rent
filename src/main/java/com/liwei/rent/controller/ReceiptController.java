package com.liwei.rent.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.entity.Receipt;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.ReceiptVO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.IReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 收据表 前端控制器
 * </p>
 *
 * @author liwei
 * @since 2024-02-18
 */
@RestController
@RequestMapping("/receipt")
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);
    @Autowired
    private IReceiptService receiptService;

    @PostMapping(value = "/create")
    public Result<Void> createReceipt(@RequestBody ReceiptVO receiptVO){
        receiptService.createReceipt(receiptVO);
        return Result.ok();
    }

    @GetMapping(value = "/list")
    public Result<PageDTO<ReceiptDTO>> listReceipt(@ModelAttribute("receiptVO") ReceiptVO receiptVO, @ModelAttribute("pageVO") PageVO pageVO){
        PageDTO<ReceiptDTO> listReceipt = receiptService.listReceipt(receiptVO, pageVO);
        return Result.build(listReceipt);
    }

    @DeleteMapping(value = "/del")
    public Result<Void> delReceipt(Integer id){
        Receipt receipt = receiptService.getById(id);
        receipt.setDelFlag(DelFlagEnum.DEL.value());
        receiptService.save(receipt);
        return Result.ok();
    }

}
