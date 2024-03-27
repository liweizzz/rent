package com.liwei.rent.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.dto.ApartmentDTO;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.dao.ReceiptMapper;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.ReceiptVO;
import com.liwei.rent.entity.Receipt;
import com.liwei.rent.service.IApartmentService;
import com.liwei.rent.service.IReceiptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import gui.ava.html.Html2Image;
import jodd.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 收据表 服务实现类
 * </p>
 *
 * @author liwei
 * @since 2024-02-18
 */
@Service
@Slf4j
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements IReceiptService {
    @Value("${receipt.folderPath}")
    private String receiptPath;
    @Autowired
    private IApartmentService apartmentService;

    @Override
    public void createReceipt(ReceiptVO receiptVO) {
        //租金
        BigDecimal rentMoney = new BigDecimal(receiptVO.getRentMoney());
        //押金
        BigDecimal deposit = StringUtils.isEmpty(receiptVO.getDeposit()) ? BigDecimal.ZERO : new BigDecimal(receiptVO.getDeposit());
        //上次电表度数
        Integer lastElecNum = receiptVO.getLastElecNum();
        //本次电表度数
        Integer curElecNum = receiptVO.getCurElecNum();
        //电费单价
        BigDecimal elecPrice = new BigDecimal(receiptVO.getElecPrice());
        //电费
        BigDecimal elecMoney = new BigDecimal(curElecNum - lastElecNum).multiply(elecPrice);
        //水费单价
        BigDecimal waterMoney = new BigDecimal(receiptVO.getWaterMoney());
        //总水费 = 人数*水费单价
        BigDecimal waterMoneyCount = new BigDecimal(receiptVO.getWaterMoney()).multiply(new BigDecimal(receiptVO.getPeopleCount()));
        //网费
        BigDecimal internetMoney = StringUtils.isEmpty(receiptVO.getInternetMoney()) ? BigDecimal.ZERO :  new BigDecimal(receiptVO.getInternetMoney());
        //金额合计
        BigDecimal sumMoney = rentMoney.add(elecMoney).add(waterMoneyCount).add(internetMoney);
        //金额合计（包含押金）
        BigDecimal sumMoneyWithDeposit = rentMoney.add(elecMoney).add(waterMoneyCount).add(internetMoney).add(deposit);

        //生成收据
        fillReceipt(receiptVO,waterMoneyCount,elecMoney,sumMoney,sumMoneyWithDeposit);
        //保存数据库
        Receipt receipt = new Receipt();
        BeanUtils.copyProperties(receiptVO,receipt);
        LocalDateTime now = LocalDateTime.now();
        receipt.setRentMoney(rentMoney);
        receipt.setElecPrice(elecPrice);
        receipt.setElecMoney(elecMoney);
        receipt.setWaterMoney(waterMoney);
        receipt.setInternetMoney(internetMoney);
        receipt.setSumMoney(sumMoney);
        receipt.setCreateTime(now);
        receipt.setUpdateTime(now);
        receipt.setDelFlag(DelFlagEnum.UN_DEL.value());
        this.save(receipt);
    }

    /**
     * @param receiptVO
     * @param elecMoney 电费
     * @param sumMoney 金额合计
     */
    private void fillReceipt(ReceiptVO receiptVO,BigDecimal waterMoneyCount,BigDecimal elecMoney,BigDecimal sumMoney,BigDecimal sumMoneyWithDeposit){
        // 配置 FreeMarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        // 设置模板文件所在的目录
        Writer fileWriter = null;
        File htmlFile = null;
        try {
            cfg.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
            // 加载模板文件
            Template template = cfg.getTemplate("receipt.html");
            // 创建数据模型
            Map<String, Object> data = new HashMap<>();
            LocalDate now = LocalDate.now();
            //房间号
            data.put("roomNum", receiptVO.getRoomNum());
            //年
            data.put("year", now.getYear());
            //月
            data.put("month", now.getMonthValue());
            //日
            data.put("day", now.getDayOfMonth());
            //房租
            data.put("rentMoney", receiptVO.getRentMoney());
            //起始日期
            data.put("rentStartDay", receiptVO.getRentStartDay());
            //结束日期
            data.put("rentEndDay", receiptVO.getRentEndDay());
            //电费单价
            data.put("elecPrice", receiptVO.getElecPrice());
            //期初电表度数
            data.put("curElecNum", receiptVO.getCurElecNum());
            //期末电表度数
            data.put("lastElecNum", receiptVO.getLastElecNum());
            //电费
            data.put("elecMoney", elecMoney.toPlainString());
            //水费
            data.put("waterMoney", receiptVO.getWaterMoney());
            //人数
            data.put("peopleCount", receiptVO.getPeopleCount());
            //总水费
            data.put("waterMoneyCount", waterMoneyCount);
            //网费
            data.put("internetMoney", StringUtils.isEmpty(receiptVO.getInternetMoney()) ? 0 : receiptVO.getInternetMoney());
            //备注
            data.put("note", StringUtils.isEmpty(receiptVO.getNote()) ? "" : receiptVO.getNote());
            //收款人签名
            data.put("signature", receiptVO.getSignature());
            //金额合计= 租金+电费+水费+网费
            data.put("sumMoney", sumMoney.toPlainString());
            //金额合计（包含押金）= 租金+电费+水费+网费+押金
            data.put("sumMoneyWithDeposit",sumMoneyWithDeposit.toPlainString());
            // 渲染模板
            htmlFile = new File("src/main/resources/static/"+receiptVO.getRoomNum()+".html");
            fileWriter  = new FileWriter(htmlFile);
            template.process(data, fileWriter);
            // 将 HTML文件转换为图片
            String folderPath = receiptPath + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String fileName =  receiptVO.getRoomNum() + ".png";
            File folder = new File(folderPath);
            if(!folder.exists()){
                folder.mkdirs();
            }
            Html2Image.fromHtml(FileUtil.readString(htmlFile))
                    .getImageRenderer()
                    .setWidth(660)
                    .saveImage(folderPath + "/" + fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fileWriter.close();
                //删除生成的html文件
                htmlFile.delete();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public PageDTO<ReceiptDTO> listReceipt(ReceiptVO receiptVO, PageVO pageVO) {
        String userId = receiptVO.getUserId();
        if(StringUtils.isEmpty(userId)){
            log.info("用户（房东）ID为空");
            throw new RentException(ErrorCodeEnum.USER_ID_IS_NULL);
        }
        //设置公寓ID
        String apartmentId = receiptVO.getApartmentId();
        if(StringUtils.isEmpty(apartmentId)){
            List<ApartmentDTO> apartmentDTOS = apartmentService.listApartmentByUserId(userId);
            if(CollectionUtils.isEmpty(apartmentDTOS)){
                log.info("此房东暂无公寓，房东ID：{}",userId);
                return null;
            }
            apartmentId = apartmentDTOS.get(0).getApartmentId();
        }
        PageDTO<ReceiptDTO> receiptDTOPageDTO = new PageDTO<>();

        PageDTO<Receipt> page = new PageDTO<>();
        page.setCurrent(pageVO.getPageNum());
        page.setSize(pageVO.getPageSize());
        LambdaQueryWrapper<Receipt> cond = new LambdaQueryWrapper<>();
        if(receiptVO.getRoomNum() != null){
            cond.eq(Receipt::getRoomNum,receiptVO.getRoomNum());
        }
        cond.eq(Receipt::getApartmentId,apartmentId).eq(Receipt::getDelFlag,DelFlagEnum.UN_DEL.value());
        PageDTO<Receipt> receiptPageDTO = this.baseMapper.selectPage(page, cond);
        BeanUtils.copyProperties(receiptPageDTO,receiptDTOPageDTO);
        return receiptDTOPageDTO;
    }

}
