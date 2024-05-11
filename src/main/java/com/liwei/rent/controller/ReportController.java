package com.liwei.rent.controller;

import com.liwei.rent.common.dto.Result;
import com.liwei.rent.service.IReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportController {
    @Autowired
    private IReceiptService receiptService;

    @GetMapping("/downloadReport")
    public Result<Void> downloadReport(String apartmentId,String month) {
        receiptService.exportReceipt(apartmentId,month);
        return Result.ok();
    }
}
