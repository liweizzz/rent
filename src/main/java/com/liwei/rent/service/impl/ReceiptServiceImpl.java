package com.liwei.rent.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.liwei.rent.common.Enum.DelFlagEnum;
import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.exception.EasyExcelCellWriteHandler;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.DateUtils;
import com.liwei.rent.common.utils.IdUtils;
import com.liwei.rent.dao.ReceiptMapper;
import com.liwei.rent.common.dto.ReceiptDTO;
import com.liwei.rent.common.vo.PageVO;
import com.liwei.rent.common.vo.ReceiptVO;
import com.liwei.rent.entity.Receipt;
import com.liwei.rent.service.IReceiptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import gui.ava.html.Html2Image;
import jodd.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Value("${report.templet}")
    private String templetPath;
    @Value("${report.path}")
    private String reportPath;

    @Override
    public void createReceipt(ReceiptVO receiptVO) {
        this.vaildParam(receiptVO);
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
        this.saveToDB(receiptVO,rentMoney,elecPrice,elecMoney,waterMoney,internetMoney,sumMoney);
    }

    private void vaildParam(ReceiptVO receiptVO){
        if(receiptVO.getCurElecNum() == null || receiptVO.getCurElecNum() < 0){
            throw new RentException(ErrorCodeEnum.RECEIPT_CUR_ELECNUM_IS_ERROR);
        }
    }

    /**
     * @param receiptVO
     * @param elecMoney 电费
     * @param sumMoney 金额合计
     */
    private void fillReceipt(ReceiptVO receiptVO,BigDecimal waterMoneyCount,BigDecimal elecMoney,BigDecimal sumMoney,
                             BigDecimal sumMoneyWithDeposit){
        // 配置 FreeMarker
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_31);
        // 设置模板文件所在的目录
        Writer fileWriter = null;
        File htmlFile = null;
        try {
            cfg.setClassLoaderForTemplateLoading(ReceiptServiceImpl.class.getClassLoader(), "templates");
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
            htmlFile = new File(receiptPath+receiptVO.getRoomNum()+".html");
            if(!htmlFile.exists()){
                htmlFile.createNewFile();
            }
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
                if(fileWriter != null){
                    fileWriter.close();
                }
                //删除生成的html文件
                if(htmlFile != null){
                    htmlFile.delete();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 保存数据库
     */
    private void saveToDB(ReceiptVO receiptVO,BigDecimal rentMoney,BigDecimal elecPrice,BigDecimal elecMoney,
                          BigDecimal waterMoney,BigDecimal internetMoney,BigDecimal sumMoney){
        LocalDateTime now = LocalDateTime.now();
        Receipt receipt;
        if(receiptVO.getId() != null && receiptVO.getId() != 0){
            //已存在该收据，更新
            receipt = this.getById(receiptVO.getId());
        }else {
            receipt = new Receipt();
            receipt.setDelFlag(DelFlagEnum.UN_DEL.value());
            receipt.setCreateTime(now);
        }
        BeanUtils.copyProperties(receiptVO,receipt);
        receipt.setRentMoney(rentMoney);
        receipt.setElecPrice(elecPrice);
        receipt.setElecMoney(elecMoney);
        receipt.setWaterMoney(waterMoney);
        receipt.setInternetMoney(internetMoney);
        receipt.setSumMoney(sumMoney);
        receipt.setUpdateTime(now);
        this.saveOrUpdate(receipt);
    }

    @Override
    public PageDTO<ReceiptDTO> listReceipt(ReceiptVO receiptVO, PageVO pageVO) {
        PageDTO<ReceiptDTO> receiptDTOPageDTO = new PageDTO<>();
        PageDTO<Receipt> page = new PageDTO<>();
        page.setCurrent(pageVO.getPageNum());
        page.setSize(pageVO.getPageSize());
        LambdaQueryWrapper<Receipt> cond = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(receiptVO.getRoomNum())){
            cond = cond.eq(Receipt::getRoomNum,receiptVO.getRoomNum());
        }
        if(StringUtils.isNotEmpty(receiptVO.getMonth())){
            cond.likeRight(Receipt::getCreateTime,receiptVO.getMonth());
        }
        //设置公寓ID
        cond.eq(Receipt::getApartmentId,receiptVO.getApartmentId())
                .eq(Receipt::getDelFlag,DelFlagEnum.UN_DEL.value())
                .orderByDesc(Receipt::getCreateTime).orderByAsc(Receipt::getRoomNum);
        PageDTO<Receipt> receiptPageDTO = this.baseMapper.selectPage(page, cond);
        List<ReceiptDTO> collect = receiptPageDTO.getRecords().stream().map(receipt -> {
            ReceiptDTO receiptDTO = new ReceiptDTO();
            BeanUtils.copyProperties(receipt,receiptDTO);
            receiptDTO.setRoomNum(Integer.valueOf(receipt.getRoomNum()));
            receiptDTO.setTenantName(IdUtils.maskName(receipt.getTenantName()));
            return receiptDTO;
        }).collect(Collectors.toList());
        BeanUtils.copyProperties(receiptPageDTO,receiptDTOPageDTO);
        receiptDTOPageDTO.setRecords(collect);
        return receiptDTOPageDTO;
    }

    @Override
    public byte[] getReceiptImg(Integer id) {
        Receipt receipt = this.getById(id);
        if(receipt == null){
            throw new RentException(ErrorCodeEnum.RECEIPT_IS_NOT_EXIST);
        }
        String roomNum = receipt.getRoomNum();
        String url = receiptPath + DateUtils.getStrDateTime(receipt.getUpdateTime()) + "/" + roomNum + ".png";
        // 读取图片
        BufferedImage image;
        try {
            image = ImageIO.read(new File(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (image == null) {
            throw new RentException(ErrorCodeEnum.RECEIPT_IS_NOT_EXIST);
        }
        // 将BufferedImage转换为byte数组
        ByteArrayOutputStream bos = null;
        byte[] imageBytes;
        try {
            bos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bos);
            imageBytes = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if(bos != null){
                try {
                    bos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return imageBytes;
    }

    @Override
    public void exportReceipt(String apartmentId,String month,HttpServletResponse response) {
        List<Receipt> receiptList = this.lambdaQuery().eq(Receipt::getApartmentId, apartmentId)
                .likeRight(Receipt::getCreateTime, month)
                .eq(Receipt::getDelFlag, DelFlagEnum.UN_DEL.value())
                .orderByAsc(Receipt::getRoomNum).list();

        List<ReceiptDTO> data = receiptList.stream().map(receipt -> {
            ReceiptDTO receiptDTO = new ReceiptDTO();
            BeanUtils.copyProperties(receipt, receiptDTO);
            receiptDTO.setRoomNum(Integer.valueOf(receipt.getRoomNum()));
            receiptDTO.setDeposit(receipt.getDeposit() == null ? BigDecimal.ZERO : receipt.getDeposit());
            return receiptDTO;
        }).collect(Collectors.toList());
        // 创建ExcelWriter对象
        if(CollectionUtils.isEmpty(data)){
            log.info("当月数据为空，月份：{}",month);
           return;
        }

        File report = new File(reportPath);
        if(!report.exists()){
            try {
                report.createNewFile();
            } catch (IOException e) {
                log.error("报表创建失败");
                throw new RentException(ErrorCodeEnum.CREATE_REPORT_ERROR);
            }
        }
        ExcelWriter writer = EasyExcel.write(report).withTemplate(new File(templetPath)).build();
        Workbook workbook = writer.writeContext().writeWorkbookHolder().getWorkbook();
        workbook.setForceFormulaRecalculation(true);
        WriteSheet sheet = EasyExcel.writerSheet().registerWriteHandler(new EasyExcelCellWriteHandler()).build();
//        Map<String,Object> value = new HashMap<>();
//        value.put("otherMoney",500);
//        value.put("otherInfo","jgjkgjhg");
//        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();

        writer.fill(data,sheet).finish();
        this.download(response,report);
//        EasyExcel.write(fileName).withTemplate(templateFileName).sheet().doFill(data);
    }

    private void download(HttpServletResponse response,File file){
        if(!file.exists()){
            log.info("未找到报表文件");
        }
        // 设置响应头，指定文件为附件并提供文件名
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Access-Control-Expose-Headers","Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());

        // 读取文件内容并写入响应体
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            int bytesRead;
            byte[] buffer = new byte[4096];
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
        } catch (Exception e) {
            log.error("下载报表文件异常,{}",e);
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    log.error("文件流关闭异常,{}",e);
                }
            }
        }
    }

}
