package com.niranjan.transport.controller;

import com.niranjan.transport.dto.MonthlyProfitLossDTO;
import com.niranjan.transport.service.ReportService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
public class ProfitLossReportController {

    private final ReportService reportService;

    public ProfitLossReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/profit-loss/monthly")
    public MonthlyProfitLossDTO getMonthlyProfitLoss(
            @RequestParam int year,
            @RequestParam int month) {

        return reportService.getMonthlyProfitLoss(year, month);
    }
}
