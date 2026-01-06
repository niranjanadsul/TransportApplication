package com.niranjan.transport.controller;

import com.niranjan.transport.dto.DriverMonthlyAccountReport;
import com.niranjan.transport.service.DriverAccountingReportService;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/reports/drivers")
public class DriverAccountingReportController {

    private final DriverAccountingReportService service;

    public DriverAccountingReportController(
            DriverAccountingReportService service) {
        this.service = service;
    }

    @GetMapping("/monthly-account")
    public DriverMonthlyAccountReport getDriverMonthlyAccount(
            @RequestParam String startMonth,
            @RequestParam String endMonth) {

        return service.getDriverMonthlyAccountReport(
                YearMonth.parse(startMonth),
                YearMonth.parse(endMonth)
        );
    }
}
