package com.liwei.rent.controller;

import com.liwei.rent.common.Enum.ErrorCodeEnum;
import com.liwei.rent.common.dto.Result;
import com.liwei.rent.common.exception.RentException;
import com.liwei.rent.common.utils.DateUtils;
import com.liwei.rent.service.IReceiptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private IReceiptService receiptService;

    @GetMapping("/download")
    public Result<Void> downloadReport(String apartmentId, String month, HttpServletResponse response) {
        if(StringUtils.isEmpty(apartmentId)){
            throw new RentException(ErrorCodeEnum.APPARTENT_IS_NULL);
        }
        if(StringUtils.isEmpty(month)){
            throw new RentException(ErrorCodeEnum.MONTH_IS_NULL);
        }
        Date dateCurYearAndMonth = DateUtils.getYearAndMonthAsDate();
        Date date = DateUtils.getYearAndMonthAsDate(month);
        if(date.after(dateCurYearAndMonth)){
            throw new RentException(ErrorCodeEnum.MONTH_IS_ILLEGAL);
        }
        receiptService.exportReceipt(apartmentId,month,response);
        return Result.ok();
    }
}
