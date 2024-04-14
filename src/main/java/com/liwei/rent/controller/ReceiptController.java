package com.liwei.rent.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.DateUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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
    @Value("${receipt.folderPath}")
    private String receiptUrl;

    @PostMapping(value = "/create")
    public Result<Void> createReceipt(@RequestBody ReceiptVO receiptVO){
        receiptService.createReceipt(receiptVO);
        return Result.ok();
    }

    @GetMapping(value = "/getReceipt")
    public Result<ReceiptDTO> getReceipt(Integer id){
        ReceiptDTO res = new ReceiptDTO();
        Receipt receipt = receiptService.getById(id);
        if(receipt == null){
            throw new RentException(ErrorCodeEnum.RECEIPT_IS_NOT_EXIST);
        }
        BeanUtils.copyProperties(receipt,res);
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
        BeanUtils.copyProperties(receipt,res);
        return Result.build(res);
    }

    @GetMapping(value = "/getReceiptImg")
    public Result<Void> getReceipt(Integer id, HttpServletResponse response) throws IOException {
        Receipt receipt = receiptService.getById(id);
        if(receipt == null){
            throw new RentException(ErrorCodeEnum.RECEIPT_IS_NOT_EXIST);
        }
        String roomNum = receipt.getRoomNum();
        String url = receiptUrl + DateUtils.getStrDateTime(receipt.getCreateTime()) + "/" + roomNum + ".png";
        // 读取图片
        BufferedImage image = ImageIO.read(new File(url));
        if (image == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return Result.ok();
        }
        // 将BufferedImage转换为byte数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos); // 假设返回的是jpg格式的图片
        byte[] imageBytes = baos.toByteArray();
        // 设置响应头
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentLength(imageBytes.length);
        // 写入响应流
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(imageBytes);
            outputStream.flush();
        }
        return Result.ok();
    }

}
