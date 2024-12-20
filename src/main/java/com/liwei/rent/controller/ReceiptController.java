package com.liwei.rent.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.annotation.PermissionCheck;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.vo.ReceiptBatchVO;
import com.liwei.rent.entity.Receipt;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.ReceiptVO;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.IReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public Result<Void> createReceipt(@Valid @RequestBody ReceiptVO receiptVO){
        receiptService.createReceipt(receiptVO);
        return Result.ok();
    }

    @PostMapping(value = "/createBatch")
    public Result<Void> createReceiptBatch(@RequestBody List<ReceiptBatchVO> batchList){
        receiptService.createReceiptBatch(batchList);
        return Result.ok();
    }

    @GetMapping(value = "/getReceipt")
    @PermissionCheck("receipt:get")
    public Result<ReceiptDTO> getReceipt(Integer id){
        ReceiptDTO res = new ReceiptDTO();
        Receipt receipt = receiptService.getById(id);
        if(receipt == null){
            throw new RentException(ErrorCodeEnum.RECEIPT_IS_NOT_EXIST);
        }
        BeanUtils.copyProperties(receipt,res);
        res.setRoomNum(Integer.valueOf(receipt.getRoomNum()));
        return Result.build(res);
    }

    @GetMapping(value = "/list")
    public Result<PageDTO<ReceiptDTO>> listReceipt(@ModelAttribute("receiptVO") ReceiptVO receiptVO, @ModelAttribute("pageVO") PageVO pageVO){
        PageDTO<ReceiptDTO> listReceipt = receiptService.listReceipt(receiptVO, pageVO);
        return Result.build(listReceipt);
    }

    @DeleteMapping(value = "/del")
    public Result<Void> delReceipt(Integer id){
        receiptService.lambdaUpdate().eq(Receipt::getId,id)
                .set(Receipt::getDelFlag,DelFlagEnum.DEL.value()).update();
        return Result.ok();
    }

    @GetMapping(value = "/getLastReceiptByRoom")
    public Result<ReceiptDTO> getLastReceiptByRoom(String roomNum){
        if(StringUtils.isEmpty(roomNum)){
            throw new RentException(ErrorCodeEnum.ROOM_NUM_IS_NULL);
        }
        Receipt receipt = receiptService.lambdaQuery().eq(Receipt::getRoomNum,roomNum)
                .eq(Receipt::getDelFlag,DelFlagEnum.UN_DEL)
                .orderByDesc(Receipt::getCreateTime).last("limit 1").one();
        ReceiptDTO res = new ReceiptDTO();
        if(receipt != null){
            BeanUtils.copyProperties(receipt,res);
        }
        return Result.build(res);
    }

    @GetMapping(value = "/getReceiptImg")
    public Result<byte[]> getReceiptImg(Integer id){
        byte[] receiptImg = receiptService.getReceiptImg(id);
        return Result.build(receiptImg);
    }

}
